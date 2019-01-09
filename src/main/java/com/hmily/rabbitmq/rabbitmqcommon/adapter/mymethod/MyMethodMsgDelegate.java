package com.hmily.rabbitmq.rabbitmqcommon.adapter.mymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMethodMsgDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(MyMethodMsgDelegate.class);
	
    //这个handleMessage方法名要根据org.springframework.amqp.rabbit.listener.adapter包下的
    //      MessageListenerAdapter.ORIGINAL_DEFAULT_LISTENER_METHOD的默认值来确定
    public void handleMessage(byte[] messageBody) {
        log.info("默认方法, 消息内容: {}", new String(messageBody));
    }

}
