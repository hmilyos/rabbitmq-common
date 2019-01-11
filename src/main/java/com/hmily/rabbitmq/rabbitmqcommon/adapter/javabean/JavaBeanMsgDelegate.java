package com.hmily.rabbitmq.rabbitmqcommon.adapter.javabean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hmily.rabbitmq.rabbitmqcommon.entity.Order;

public class JavaBeanMsgDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(JavaBeanMsgDelegate.class);
	
    
    public void consumeMessage(Order order) {
        log.info("order对象: {}", order.toString());
    }

}
