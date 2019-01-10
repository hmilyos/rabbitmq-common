package com.hmily.rabbitmq.rabbitmqcommon.adapter.manymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManyMethodMsgDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(ManyMethodMsgDelegate.class);
	
    //  队列名称 和 方法名称 也可以进行一一的匹配
    public void method1(String messageBody) {
        log.info("method1 收到消息内容: {}", messageBody);
    }
    
    public void method2(String messageBody) {
        log.info("method2 收到消息内容: {}", messageBody);
    }

}
