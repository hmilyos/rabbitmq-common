package com.hmily.rabbitmq.rabbitmqcommon.adapter.str;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

//从字节数组转换为String
public class TextMessageConverter implements MessageConverter {

  @Override
  public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
      return new Message(object.toString().getBytes(), messageProperties);
  }

  @Override
  public Object fromMessage(Message message) throws MessageConversionException {
      String contentType = message.getMessageProperties().getContentType();
      if(null != contentType && contentType.contains("text")) {
          return new String(message.getBody());
      }
      return message.getBody();
  }

}
