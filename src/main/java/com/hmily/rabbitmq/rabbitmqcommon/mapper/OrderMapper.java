package com.hmily.rabbitmq.rabbitmqcommon.mapper;

import com.hmily.rabbitmq.rabbitmqcommon.entity.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> queryAll();
}