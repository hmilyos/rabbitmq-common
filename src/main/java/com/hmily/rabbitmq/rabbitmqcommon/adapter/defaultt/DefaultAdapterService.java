package com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAdapterService {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultAdapterService.class);
	 
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    public void sendMessage(String msg){
    	
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "信息描述..");
        messageProperties.getHeaders().put("type", "自定义消息类型..");
        Message message = new Message(msg.getBytes(), messageProperties);

        rabbitTemplate.convertAndSend(DefaultConfig.EXCHANEGE_NAME, DefaultConfig.DEFAULT_ROUTING_KEY, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                log.info("------添加额外的设置---------");
                message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
                return message;
            }
        });
    }
	
}
