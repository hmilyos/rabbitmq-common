package com.hmily.rabbitmq.rabbitmqcommon.mapper;

import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;

public interface MessageFailedMapper {
    int deleteByPrimaryKey(Long failId);

    int insert(MessageFailed record);

    int insertSelective(MessageFailed record);

    MessageFailed selectByPrimaryKey(Long failId);
    
    MessageFailed selectByMsgId(Long msgId);

    int updateByPrimaryKeySelective(MessageFailed record);

    int updateByPrimaryKey(MessageFailed record);
}