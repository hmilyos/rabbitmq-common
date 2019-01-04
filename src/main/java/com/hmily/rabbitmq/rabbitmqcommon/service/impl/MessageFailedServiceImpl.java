package com.hmily.rabbitmq.rabbitmqcommon.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hmily.dubbo.common.exception.CustomException;
import com.hmily.dubbo.common.service.ISnowFlakeServiceApi;
import com.hmily.dubbo.common.util.ResponseCode;
import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;
import com.hmily.rabbitmq.rabbitmqcommon.mapper.MessageFailedMapper;
import com.hmily.rabbitmq.rabbitmqcommon.service.IMessageFailedService;

@Service
public class MessageFailedServiceImpl implements IMessageFailedService {
	
	private static final Logger log = LoggerFactory.getLogger(MessageFailedServiceImpl.class);

    @Reference(version = "${snowFlakeServiceApi.version}",
            application = "${dubbo.application.id}",
            interfaceName = "com.hmily.dubbo.common.service.ISnowFlakeServiceApi",
            check = false,
            timeout = 1000,
            retries = 0
    )
    private ISnowFlakeServiceApi snowFlakeServiceApi;
    
	@Autowired
	private MessageFailedMapper MessageFailedMapper;
	
	@Override
	public int add(MessageFailed messageFailed) {
		messageFailed.setFailId(snowFlakeServiceApi.getSnowFlakeID());
		int row = MessageFailedMapper.insertSelective(messageFailed);
		log.info("add result row: {}, info: {}", row, messageFailed.toString());
//		if (StringUtils.equals("test_desc", messageFailed.getFailDesc())) {
//			throw  new CustomException(ResponseCode.OTHER);
//		}
		return row;
	}
}
