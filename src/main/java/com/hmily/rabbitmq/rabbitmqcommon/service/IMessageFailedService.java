package com.hmily.rabbitmq.rabbitmqcommon.service;

import org.springframework.transaction.annotation.Transactional;

import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;

public interface IMessageFailedService {
	
	@Transactional
	int add(MessageFailed messageFailed);
}
