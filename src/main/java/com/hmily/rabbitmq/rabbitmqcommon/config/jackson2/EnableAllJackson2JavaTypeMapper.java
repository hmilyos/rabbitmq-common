package com.hmily.rabbitmq.rabbitmqcommon.config.jackson2;

import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;

public class EnableAllJackson2JavaTypeMapper extends DefaultJackson2JavaTypeMapper {

    public EnableAllJackson2JavaTypeMapper(){
        super();
        setTrustedPackages("*");
    }
}
