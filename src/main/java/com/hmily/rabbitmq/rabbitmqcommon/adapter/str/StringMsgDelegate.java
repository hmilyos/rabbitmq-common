package com.hmily.rabbitmq.rabbitmqcommon.adapter.str;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringMsgDelegate {

	private static final Logger log = LoggerFactory.getLogger(StringMsgDelegate.class);
	
    public void consumeMessage(String messageBody) {
        log.info(" string 方法, 消息内容: {}", messageBody);
    }
}
