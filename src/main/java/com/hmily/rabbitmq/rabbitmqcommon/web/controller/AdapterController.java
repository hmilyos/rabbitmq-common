package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmily.dubbo.common.util.ServerResponse;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.mymethod.MyMethodAdapterService;

@RestController
public class AdapterController {
	
	@Autowired
	private DefaultAdapterService defaultAdapterService;
	
	@PostMapping("/adapter/default")
	public ServerResponse defaultAdapter(String msg) {
		defaultAdapterService.sendMessage(msg);
		return ServerResponse.createBySuccess();
	}
	
	@Autowired
	private MyMethodAdapterService myMethodAdapterService;
	
	@PostMapping("/adapter/myMethod")
	public ServerResponse myMethodAdapter(String msg) {
		myMethodAdapterService.sendMessage(msg);
		return ServerResponse.createBySuccess();
	}

}
