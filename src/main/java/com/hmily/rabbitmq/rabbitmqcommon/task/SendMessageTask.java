package com.hmily.rabbitmq.rabbitmqcommon.task;

import com.hmily.rabbitmq.rabbitmqcommon.common.Constants;
import com.hmily.rabbitmq.rabbitmqcommon.common.MSGStatusEnum;
import com.hmily.rabbitmq.rabbitmqcommon.common.TypeEnum;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;
import com.hmily.rabbitmq.rabbitmqcommon.mapper.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendMessageTask {
    private static final Logger log = LoggerFactory.getLogger(SendMessageTask.class);

    @Autowired
    private MessageMapper messageMapper;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend(){
        log.info("---------------定时任务开始---------------");
        List<Message> msgs = messageMapper.getNotProcessingInByType(TypeEnum.CREATE_ORDER.getCode(), Constants.MAX_TRY_COUNT, MSGStatusEnum.SENDING.getCode());

    }
}
