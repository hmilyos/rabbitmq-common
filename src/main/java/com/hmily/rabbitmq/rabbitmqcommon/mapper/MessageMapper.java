package com.hmily.rabbitmq.rabbitmqcommon.mapper;

import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(Long messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}