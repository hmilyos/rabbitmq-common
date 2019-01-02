package com.hmily.rabbitmq.rabbitmqcommon.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hmily.dubbo.common.service.ISnowFlakeServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmily.rabbitmq.rabbitmqcommon.util.SnowFlake;

@RestController
public class TestController {
	
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",
            interfaceName = "com.hmily.rocketmq.store.api.HelloServiceApi",
            check = false,
            timeout = 1000,
            retries = 0
    )
    private ISnowFlakeServiceApi snowFlakeServiceApi;

	@GetMapping("/test")
	public String test() {
		return "hello";
	}
	
	@GetMapping("/test/longid")
	public String testId() {
		String res = null;
//		for(int i = 0; i < 5; i++) {
//			Long id = SnowFlake.getId();
//			log.info("id: {}", id);
//			if (i == 0) {
//				res = id.toString();
//			}
//		}

        Long id = snowFlakeServiceApi.getSnowFlakeID();
        log.info("id: {}", id);
        res = id.toString();

        return res;
	}
}
