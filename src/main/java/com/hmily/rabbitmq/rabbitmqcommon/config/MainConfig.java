package com.hmily.rabbitmq.rabbitmqcommon.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = "com.hmily.rabbitmq.rabbitmqcommon.mapper")
@ComponentScan(basePackages = {"com.hmily.rabbitmq.rabbitmqcommon.*", "com.hmily.rabbitmq.rabbitmqcommon.config.*"})
public class MainConfig {

}
