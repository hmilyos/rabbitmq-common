package com.hmily.rabbitmq.rabbitmqcommon.adapter.file;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileAdapterService {
	
	private static final Logger log = LoggerFactory.getLogger(FileAdapterService.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    public void sendPDFMessage(String fileName){
        byte[] body = new byte[0];
        try {
            body = Files.readAllBytes(Paths.get("G:/test/file", fileName));
        } catch (IOException e) {
            log.error(" PDF file readAllBytes error: ", e);
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/pdf");
        Message message = new Message(body, messageProperties);
        rabbitTemplate.send(FileConfig.EXCHANEGE_NAME, FileConfig.PDF_FILE_ROUTING_KEY, message);
        
    }

    public void sendImageMessage(String fileName){

        byte[] body = new byte[0];
        try {
            body = Files.readAllBytes(Paths.get("G:/test/file", fileName));
        } catch (IOException e) {
            log.error(" image file readAllBytes error: ", e);
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("image/png");
        messageProperties.getHeaders().put("extName", "png");
        Message message = new Message(body, messageProperties);
        rabbitTemplate.send(FileConfig.EXCHANEGE_NAME, FileConfig.IMAGE_FILE_ROUTING_KEY, message);



    }
}
