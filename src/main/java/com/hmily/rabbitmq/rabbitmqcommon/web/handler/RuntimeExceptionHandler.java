package com.hmily.rabbitmq.rabbitmqcommon.web.handler;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hmily.dubbo.common.exception.CustomException;
import com.hmily.dubbo.common.util.ServerResponse;

@ControllerAdvice
public class RuntimeExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionHandler.class);
	
	@ExceptionHandler(CustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handleParametersException(CustomException ex) {
		LOG.error("error:", ex);
        return ServerResponse.createByErrorCodeMessage(ex.getCode(), ex.getMessage());
    }
	
	@ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handleRuntimeException(RuntimeException ex) {
		LOG.error("error:", ex);
        return ServerResponse.createByErrorCodeMessage(500, "接口异常,详情请查看服务端日志的异常信息");
    }
	
}
