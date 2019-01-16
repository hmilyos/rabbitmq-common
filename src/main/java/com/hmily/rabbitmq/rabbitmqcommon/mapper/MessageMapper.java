package com.hmily.rabbitmq.rabbitmqcommon.mapper;

import com.hmily.rabbitmq.rabbitmqcommon.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Long messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    void changeMessageStatus(@Param("messageId") long messageId, @Param("status") int status);

    int updataNextRetryTimeForNow(long messageId);

    List<Message> getNotProcessingInByType(@Param("type") Integer type, @Param("maxTryCount") Integer maxTryCount, @Param("status") int[] status);

    int updateTryCount(Message record);
    
    List<Message> selectFail(int status);
    
    List<Message> queryAll();
    
//    date_add(now(), interval - 5 minute)
}