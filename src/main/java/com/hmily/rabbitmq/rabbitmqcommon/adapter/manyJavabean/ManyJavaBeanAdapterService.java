package com.hmily.rabbitmq.rabbitmqcommon.adapter.manyJavabean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Order;

@Service
public class ManyJavaBeanAdapterService {
	
	private static final Logger log = LoggerFactory.getLogger(ManyJavaBeanAdapterService.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    public void sendMessage(Order order, MessageFailed messageFailed){
    	
        ObjectMapper mapper = new ObjectMapper();
        String orderStr = null;
        String messageFailedStr = null;
		try {
			orderStr = mapper.writeValueAsString(order);
			messageFailedStr = mapper.writeValueAsString(messageFailed);
		} catch (JsonProcessingException e) {
			
		}
        log.info("order json: {}", orderStr);
        MessageProperties messageProperties1 = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties1.setContentType("application/json");
        messageProperties1.getHeaders().put("__TypeId__", "order");
        Message message1 = new Message(orderStr.getBytes(), messageProperties1);
        rabbitTemplate.send(ManyJavabeanConfig.EXCHANEGE_NAME, ManyJavabeanConfig.MANY_JAVA_ROUTING_KEY, message1);
        
        MessageProperties messageProperties2 = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties2.setContentType("application/json");
        messageProperties2.getHeaders().put("__TypeId__", "messageFailed");
        Message message2 = new Message(messageFailedStr.getBytes(), messageProperties2);
        rabbitTemplate.send(ManyJavabeanConfig.EXCHANEGE_NAME, ManyJavabeanConfig.MANY_JAVA_ROUTING_KEY, message2);
        
    }
}
