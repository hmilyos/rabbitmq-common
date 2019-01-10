package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmily.dubbo.common.util.ServerResponse;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.manymethod.ManyMethodAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.mymethod.MyMethodAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.str.StringAdapterService;

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
	
	@Autowired
	private StringAdapterService stringAdapterService;
	
	@PostMapping("/adapter/string")
	public ServerResponse stringAdapter(String msg) {
		stringAdapterService.sendMessage(msg);
		return ServerResponse.createBySuccess();
	}
	
	@Autowired
	private ManyMethodAdapterService manyMethodAdapterService;
	@PostMapping("/adapter/manyMethod")
	public ServerResponse manyMethod(String msg) {
		manyMethodAdapterService.sendMessage(msg);
		return ServerResponse.createBySuccess();
	}

}
