package com.hmily.rabbitmq.rabbitmqcommon.adapter.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileMessageDelegate {

    private static final Logger log = LoggerFactory.getLogger(FileMessageDelegate.class);

    public void consumeMessage(File file) {
        log.info("文件对象 方法, 消息内容: {}", file.getName());
    }
}
