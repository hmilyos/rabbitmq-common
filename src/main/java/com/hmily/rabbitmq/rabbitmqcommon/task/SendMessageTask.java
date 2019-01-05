package com.hmily.rabbitmq.rabbitmqcommon.task;

import com.hmily.rabbitmq.rabbitmqcommon.common.Constants;
import com.hmily.rabbitmq.rabbitmqcommon.common.MSGStatusEnum;
import com.hmily.rabbitmq.rabbitmqcommon.common.TypeEnum;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;
import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;
import com.hmily.rabbitmq.rabbitmqcommon.mapper.MessageMapper;
import com.hmily.rabbitmq.rabbitmqcommon.producer.RabbitOrderSender;
import com.hmily.rabbitmq.rabbitmqcommon.service.IMessageFailedService;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SendMessageTask {
    private static final Logger log = LoggerFactory.getLogger(SendMessageTask.class);

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private IMessageFailedService messageFailedService;

    @Autowired
    private RabbitOrderSender rabbitOrderSender;

//    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend(){
        log.info("---------------定时任务开始---------------");
        List<Message> msgs = messageMapper.getNotProcessingInByType(TypeEnum.CREATE_ORDER.getCode(), null, 
        		new int[]{MSGStatusEnum.SENDING.getCode()});
        msgs.forEach(msg -> {
        	if (msg.getTryCount() >= Constants.MAX_TRY_COUNT) {
//        		如果重试次数大于最大重试次数就不再重试，记录失败
        		msg.setStatus(MSGStatusEnum.SEND_FAILURE.getCode());
        		msg.setUpdateTime(new Date());
        		messageMapper.updateByPrimaryKeySelective(msg);
        		MessageFailed failed = new MessageFailed(msg.getMessageId(), "消息发送失败", "已达到最大重试次数，但是还是发送失败");
        		messageFailedService.add(failed);
			} else {
//				未达到最大重试次数，可以进行重发消息
//				先改一下消息记录，保存好再发送消息
				msg.setNextRetry(DateUtils.addMinutes(new Date(), Constants.TRY_TIMEOUT));
				int row = messageMapper.updateTryCount(msg);
                try {
                    rabbitOrderSender.sendOrder(msg);
                } catch (Exception e) {
                    log.error("sendOrder mq msg error: ", e);
                    messageMapper.updataNextRetryTimeForNow(msg.getMessageId());
                }
            }
        });
    }
}
