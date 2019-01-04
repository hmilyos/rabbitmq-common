package com.hmily.rabbitmq.rabbitmqcommon.producer;

import com.hmily.rabbitmq.rabbitmqcommon.common.MSGStatusEnum;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;
import com.hmily.rabbitmq.rabbitmqcommon.mapper.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class RabbitOrderSender {

    private static final Logger log = LoggerFactory.getLogger(RabbitOrderSender.class);

    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageMapper messageMapper;

    @Value("${order.rabbitmq.listener.order.exchange.name}")
    private String exchangeName;

    @Value("${order.rabbitmq.send.create.key}")
    private String routingKey;

    //发送消息方法调用: 构建自定义对象消息
    public void sendOrder(Message message) throws Exception {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        CorrelationData correlationData = new CorrelationData(message.getMessageId() + "");
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, correlationData);
    }

    //回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("correlationData: {}", correlationData);
            String messageId = correlationData.getId();
            if(ack){
                //如果confirm返回成功 则进行更新
                messageMapper.changeMessageStatus(Long.parseLong(messageId), MSGStatusEnum.SEND_SUCCESS.getCode());
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                log.error("消息发送失败，需要进行异常处理...");
                messageMapper.updataNextRetryTimeForNow(Long.parseLong(messageId));
            }
        }
    };

    //回调函数: return返回
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode,
                                    String replyText, String exchange, String routingKey) {
            log.info("return exchange: {}, routingKey: {}, replyCode: {}, replyText: {}",
                    exchange, routingKey, replyCode, replyText);
        }
    };
}
