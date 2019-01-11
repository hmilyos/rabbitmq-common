package com.hmily.rabbitmq.rabbitmqcommon.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hmily.dubbo.common.exception.ParameterException;
import com.hmily.dubbo.common.service.ISnowFlakeServiceApi;
import com.hmily.rabbitmq.rabbitmqcommon.service.ISnowFlakeService;
import com.hmily.rabbitmq.rabbitmqcommon.util.SnowFlake;

@Service
public class SnowFlakeServiceImpl implements ISnowFlakeService {
	
	private final static Logger log = LoggerFactory.getLogger(SnowFlakeServiceImpl.class);
	
	@Reference(version = "${snowFlakeServiceApi.version}",
            application = "${dubbo.application.id}",
            interfaceName = "com.hmily.dubbo.common.service.ISnowFlakeServiceApi",
            check = false,
            timeout = 1000,
            retries = 0
    )
    private ISnowFlakeServiceApi snowFlakeServiceApi;

	@Override
	public long getSnowFlakeID() {
		try {
			return snowFlakeServiceApi.getSnowFlakeID();
		} catch (Exception e) {
			log.error(" RPC snowFlakeServiceApi getSnowFlakeID: ", e);
		}
		return SnowFlake.getId();
	}

	@Override
	public long[] getSnowFlakeIDs(int size) {
		if (size < 1) {
			throw new ParameterException(500, " size is illegal");
		}
		try {
			return snowFlakeServiceApi.getSnowFlakeIDs(size);
		} catch (Exception e) {
			log.error(" RPC snowFlakeServiceApi getSnowFlakeIDs: ", e);
		}
		
		long[] ids = new long[size];
		for (int i = 0; i < size; i++) {
            long id = SnowFlake.getId();
			ids[i] = id;
            log.info("id: {}", id);
		}
		return ids;
	}

}
