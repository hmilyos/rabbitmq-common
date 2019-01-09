package com.hmily.rabbitmq.rabbitmqcommon.adapter.mymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMethodMsgDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(MyMethodMsgDelegate.class);
	
    public void consumeMessage(byte[] messageBody) {
        log.info(" my method 字节数组方法, 消息内容:" + new String(messageBody));
    }

}
