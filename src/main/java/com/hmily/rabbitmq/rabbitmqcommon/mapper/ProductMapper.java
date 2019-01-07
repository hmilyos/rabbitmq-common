package com.hmily.rabbitmq.rabbitmqcommon.mapper;

import com.hmily.rabbitmq.rabbitmqcommon.entity.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}