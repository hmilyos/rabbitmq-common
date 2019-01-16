package com.hmily.rabbitmq.rabbitmqcommon.service;

import com.hmily.dubbo.common.util.ServerResponse;


import org.springframework.transaction.annotation.Transactional;

public interface IMessageService {

    @Transactional
    ServerResponse createOrder(long userId);

}
