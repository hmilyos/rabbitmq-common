package com.hmily.rabbitmq.rabbitmqcommon.conusmer;

import com.hmily.rabbitmq.rabbitmqcommon.common.MSGStatusEnum;
import com.hmily.rabbitmq.rabbitmqcommon.mapper.MessageMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消费端
 * @author ly-oushim
 *
 */
@Component
public class RabbitOrderReceiver {

    private static final Logger log = LoggerFactory.getLogger(RabbitOrderReceiver.class);

    @Autowired
    private MessageMapper messageMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${order.rabbitmq.listener.order.queue.name}",
                    durable="${order.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${order.rabbitmq.listener.order.exchange.name}",
                    durable="${order.rabbitmq.listener.order.exchange.durable}",
                    type= "${order.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${order.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${order.rabbitmq.listener.order.key}"
    )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload com.hmily.rabbitmq.rabbitmqcommon.entity.Message msg,
                               Channel channel,
                               @Headers Map<String, Object> headers) throws Exception {
        log.info("-----------------RabbitOrderReceiver---------------------");
//        channel.basicQos(0, 1, false);
        log.info("消费端 order msg: {} ",  msg.toString());
        msg.setStatus(MSGStatusEnum.PROCESSING_IN.getCode());
        int row = messageMapper.updateByPrimaryKeySelective(msg);
        if (row != 0) {
        	Thread.sleep(2000L);
            Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            //手工ACK
            channel.basicAck(deliveryTag, false);
//            接着去执行你对应的业务逻辑，
//            注意，这是可靠性投递，执行业务逻辑一定要做幂等性
        }

    }
}
