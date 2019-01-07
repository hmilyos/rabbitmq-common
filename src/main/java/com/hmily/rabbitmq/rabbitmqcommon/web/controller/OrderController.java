package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import com.hmily.dubbo.common.util.ServerResponse;
import com.hmily.rabbitmq.rabbitmqcommon.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private IMessageService messageService;

    @GetMapping("/createOrder")
    public ServerResponse createOrder(long userId){
        return messageService.createOrder(userId);
    }
    
    @GetMapping("/selectFail")
    public ServerResponse selectFail(){
    	return ServerResponse.createBySuccess(messageService.selectFail());
    }
    
    @GetMapping("/queryAll")
    public ServerResponse queryAll(){
    	return ServerResponse.createBySuccess(messageService.queryAll());
    }

    @GetMapping("/queryAllMsg")
    public ServerResponse queryAllMsg(){
        return ServerResponse.createBySuccess(messageService.queryAllMsg());
    }
}
