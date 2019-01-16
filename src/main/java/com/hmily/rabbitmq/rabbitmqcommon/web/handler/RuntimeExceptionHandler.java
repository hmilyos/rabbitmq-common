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

    /**
     * 捕捉自定义异常处理
     * @param ex
     * @return
     */
	@ExceptionHandler(CustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handleParametersException(CustomException ex) {
		LOG.error("error:", ex);
        return ServerResponse.createByErrorCodeMessage(ex.getCode(), ex.getMessage());
    }

    /**
     *  全局运行时异常处理
     * @param ex
     * @return
     */
	@ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handleRuntimeException(RuntimeException ex) {
		LOG.error("error:", ex);
        return ServerResponse.createByErrorCodeMessage(500, "接口异常,详情请查看服务端日志的异常信息");
    }
}
