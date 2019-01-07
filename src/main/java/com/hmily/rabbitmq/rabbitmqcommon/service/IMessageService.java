package com.hmily.rabbitmq.rabbitmqcommon.service;

import com.hmily.dubbo.common.util.ServerResponse;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IMessageService {

    @Transactional
    ServerResponse createOrder(long userId);
    
    List queryAll();
    
    List<Message> selectFail();

    List<Message> queryAllMsg();
}
