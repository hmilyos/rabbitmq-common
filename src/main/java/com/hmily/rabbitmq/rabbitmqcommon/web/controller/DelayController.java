package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import com.hmily.dubbo.common.util.ServerResponse;
import com.hmily.rabbitmq.rabbitmqcommon.delay.DelayConfig;
import com.hmily.rabbitmq.rabbitmqcommon.delay.DelayProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DelayController {
    @Autowired
    private DelayProducer delayProducer;

    @GetMapping("/testDelayMsg")
    public ServerResponse testDelayMsg(String title){
        delayProducer.sendMsg(DelayConfig.DELAY_QUEUE_NAME, new StringBuilder(title));
        return ServerResponse.createBySuccess();
    }
}
