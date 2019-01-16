package com.hmily.rabbitmq.rabbitmqcommon.service.impl;

import com.hmily.dubbo.common.exception.CustomException;
import com.hmily.dubbo.common.util.ServerResponse;
import com.hmily.rabbitmq.rabbitmqcommon.common.Constants;
import com.hmily.rabbitmq.rabbitmqcommon.common.MSGStatusEnum;
import com.hmily.rabbitmq.rabbitmqcommon.common.TypeEnum;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;
import com.hmily.rabbitmq.rabbitmqcommon.mapper.MessageMapper;
import com.hmily.rabbitmq.rabbitmqcommon.producer.RabbitOrderSender;
import com.hmily.rabbitmq.rabbitmqcommon.service.IMessageService;
import com.hmily.rabbitmq.rabbitmqcommon.service.ISnowFlakeService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class MessageServiceImpl implements IMessageService {

    private final  static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private ISnowFlakeService snowFlakeService;

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    @Override
    public ServerResponse createOrder(long userId) {
//        首先是针对业务逻辑，进行下单的业务，保存到数据库后
//        业务落库后，再对消息进行落库，
        long msgId = snowFlakeService.getSnowFlakeID();
        Message message = new Message(msgId, TypeEnum.CREATE_ORDER.getCode(), userId + "创建订单：" + msgId,
                0, MSGStatusEnum.SENDING.getCode(), DateUtils.addMinutes(new Date(), Constants.TRY_TIMEOUT));
        int row = messageMapper.insertSelective(message);
        if (row == 0){
            throw new CustomException(500, "消息入库异常");
        }
//        消息落库后就可以发送消息了
        try {
            rabbitOrderSender.sendOrder(message);
        } catch (Exception e) {
//        	因为业务已经落库了
//        	所以 即使发送失败也不影响，因为可靠性投递，我回去再次尝试发送消息
            log.error("sendOrder mq msg error: ", e);
            messageMapper.updataNextRetryTimeForNow(message.getMessageId());
        }
        return ServerResponse.createBySuccess();
    }

}
