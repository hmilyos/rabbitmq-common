package com.hmily.rabbitmq.rabbitmqcommon.adapter.manymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultConfig;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.str.StringConfig;

@Service
public class ManyMethodAdapterService {
	
	private static final Logger log = LoggerFactory.getLogger(ManyMethodAdapterService.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    public void sendMessage(String msg){
    	
    	MessageProperties messageProperties = new MessageProperties();
//		messageProperties.setContentType("text/plain");
		Message message = new Message(msg.getBytes(), messageProperties);

		rabbitTemplate.send(DefaultConfig.EXCHANEGE_NAME, ManyMethodConfig.MANY_METHOD_ROUTING_KEY1, message);
		rabbitTemplate.send(DefaultConfig.EXCHANEGE_NAME, ManyMethodConfig.MANY_METHOD_ROUTING_KEY2, message);
    }
}
