package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmily.rabbitmq.rabbitmqcommon.util.SnowFlake;

@RestController
public class TestController {
	
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@GetMapping("/test")
	public String test() {
		return "hello";
	}
	
	@GetMapping("/test/longid")
	public String testId() {
		String res = null;
		for(int i = 0; i < 5; i++) {
			Long id = SnowFlake.getId();
			log.info("id: {}", id);
			if (i == 0) {
				res = id.toString();
			}
		}
		return res;
	}
}
