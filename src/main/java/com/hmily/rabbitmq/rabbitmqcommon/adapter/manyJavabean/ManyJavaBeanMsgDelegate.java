package com.hmily.rabbitmq.rabbitmqcommon.adapter.manyJavabean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Order;

public class ManyJavaBeanMsgDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(ManyJavaBeanMsgDelegate.class);
	
    
    public void consumeMessage(Order order) {
        log.info("order 对象: {}", order.toString());
    }
    
    public void consumeMessage(MessageFailed messageFailed) {
        log.info("MessageFailed 对象: {}", messageFailed.toString());
    }

}
