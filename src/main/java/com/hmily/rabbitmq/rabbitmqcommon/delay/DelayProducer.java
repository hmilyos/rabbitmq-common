package com.hmily.rabbitmq.rabbitmqcommon.delay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DelayProducer {

    private static final Logger log = LoggerFactory.getLogger(DelayProducer.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String queueName, StringBuilder msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msg.append(", 发送时间：");
        msg.append(sdf.format(new Date()));
        log.info("msg: {}", msg.toString());
        rabbitTemplate.convertAndSend(DelayConfig.DELAY_EXCHANGE_NAME, DelayConfig.DELAY_ROUTING_KEY, msg.toString(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", 5000);
                return message;
            }
        });
    }
}
