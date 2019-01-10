package com.hmily.rabbitmq.rabbitmqcommon.adapter.str;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultConfig;

@Service
public class StringAdapterService {

	private static final Logger log = LoggerFactory.getLogger(StringAdapterService.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String msg) {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("text/plain");
		Message message = new Message(msg.getBytes(), messageProperties);

		rabbitTemplate.send(DefaultConfig.EXCHANEGE_NAME, StringConfig.STRING_ROUTING_KEY, message);

	}
}
