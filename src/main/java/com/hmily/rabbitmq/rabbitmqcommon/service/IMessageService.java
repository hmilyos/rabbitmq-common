package com.hmily.rabbitmq.rabbitmqcommon.service;

import com.github.pagehelper.PageInfo;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;
import com.hmily.dubbo.common.util.ServerResponse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IMessageService {

    @Transactional
    ServerResponse createOrder(long userId);
    
    List queryAll();
    
    List<Message> selectFail();

    List<Message> queryAllMsg();
    
    ServerResponse<PageInfo> queryAllByPage(int pageNum, int pageSize);
}
