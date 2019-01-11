package com.hmily.rabbitmq.rabbitmqcommon.adapter.javabean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Order;

@Service
public class JavaBeanAdapterService {
	
	private static final Logger log = LoggerFactory.getLogger(JavaBeanAdapterService.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    public void sendMessage(Order order){
    	
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
		try {
			json = mapper.writeValueAsString(order);
		} catch (JsonProcessingException e) {
			
		}
        log.info("order 4 json: {}", json);
        MessageProperties messageProperties = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties.setContentType("application/json");
        //注意这里要写你的实体类路径
        messageProperties.getHeaders().put("__TypeId__", "com.hmily.rabbitmq.rabbitmqcommon.entity.Order");
        Message message = new Message(json.getBytes(), messageProperties);

        rabbitTemplate.send(JavabeanConfig.EXCHANEGE_NAME, JavabeanConfig.JAVA_ROUTING_KEY, message);
        
    }
}
