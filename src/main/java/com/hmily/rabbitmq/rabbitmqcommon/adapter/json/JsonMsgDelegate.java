package com.hmily.rabbitmq.rabbitmqcommon.adapter.json;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMsgDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(JsonMsgDelegate.class);
	
    public void consumeMessage(Map messageBody) {
        log.info("map方法, 消息内容: {}", messageBody);
    }

}
