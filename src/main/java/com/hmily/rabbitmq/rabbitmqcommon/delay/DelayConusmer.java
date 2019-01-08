package com.hmily.rabbitmq.rabbitmqcommon.delay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DelayConusmer {

    private static final Logger log = LoggerFactory.getLogger(DelayConusmer.class);

    @RabbitListener(queues = DelayConfig.DELAY_QUEUE_NAME)
    public void receive(String msg) {
        log.info(" DelayConusmer msg: {}", msg);
    }
}
