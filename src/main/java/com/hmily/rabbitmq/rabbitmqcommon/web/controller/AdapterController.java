package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import com.hmily.rabbitmq.rabbitmqcommon.adapter.file.FileAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmily.dubbo.common.util.ServerResponse;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.javabean.JavaBeanAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.json.JsonAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.manyJavabean.ManyJavaBeanAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.manymethod.ManyMethodAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.mymethod.MyMethodAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.str.StringAdapterService;
import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;
import com.hmily.rabbitmq.rabbitmqcommon.entity.Order;
import com.hmily.rabbitmq.rabbitmqcommon.service.ISnowFlakeService;

@RestController
public class AdapterController {
	
	@Autowired
	private ISnowFlakeService snowFlakeService;
	
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
	
	@Autowired
	private JsonAdapterService jsonAdapterService;
	
	@PostMapping("/adapter/json")
	public ServerResponse jsonMethod(long userId) {
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderId(snowFlakeService.getSnowFlakeID());
		jsonAdapterService.sendMessage(order);
		return ServerResponse.createBySuccess();
	}
	
	@Autowired
	private JavaBeanAdapterService javaBeanAdapterService;
	@PostMapping("/adapter/java")
	public ServerResponse javaMethod(long userId) {
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderId(snowFlakeService.getSnowFlakeID());
		javaBeanAdapterService.sendMessage(order);
		return ServerResponse.createBySuccess();
	}
	
	@Autowired
	private ManyJavaBeanAdapterService manyJavaBeanAdapterService;
	@PostMapping("/adapter/java/many")
	public ServerResponse javaManyMethod(long userId) {
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderId(snowFlakeService.getSnowFlakeID());
		MessageFailed fail = new MessageFailed();
		fail.setFailId(snowFlakeService.getSnowFlakeID());
		fail.setMessageId(order.getOrderId());
		manyJavaBeanAdapterService.sendMessage(order, fail);
		return ServerResponse.createBySuccess();
	}

	@Autowired
    private FileAdapterService fileAdapterService;
    @PostMapping("/adapter/file/image")
    public ServerResponse imageFile(String fileName) {
        fileAdapterService.sendImageMessage(fileName);
        return ServerResponse.createBySuccess();
    }

    @PostMapping("/adapter/file/pdf")
    public ServerResponse pDfFile(String fileName) {
        fileAdapterService.sendPDFMessage(fileName);
        return ServerResponse.createBySuccess();
    }


}
