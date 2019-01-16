package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import com.hmily.rabbitmq.rabbitmqcommon.service.ISnowFlakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed;
import com.hmily.rabbitmq.rabbitmqcommon.service.IMessageFailedService;
import com.hmily.rabbitmq.rabbitmqcommon.util.SnowFlake;

@RestController
public class TestController {
	
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private ISnowFlakeService snowFlakeService;

    @GetMapping("/test/longid/rpc")
    public String testIdByRPC() {
        Long id = snowFlakeService.getSnowFlakeID();
        log.info("id: {}", id);
        return id.toString();

    }

	@GetMapping("/test")
	public String test() {
		return "hello";
	}
	
	@GetMapping("/test/longid")
	public String testIdByLocal() {
        Long id = SnowFlake.getId();
        log.info("id: {}", id);
        return id.toString();
	}
	


	
	
	
}
