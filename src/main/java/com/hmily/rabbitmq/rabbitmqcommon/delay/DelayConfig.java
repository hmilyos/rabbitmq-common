package com.hmily.rabbitmq.rabbitmqcommon.delay;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DelayConfig {

    public final static String DELAY_EXCHANGE_NAME = "spring_delay_exchange";
    public final static String DELAY_QUEUE_NAME = "spring.delay.queue";
    public final static String DELAY_ROUTING_KEY = "spring.rabbit";

    @Bean
    public CustomExchange delayExchange(){
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message",true, false,args);
    }

    @Bean
    public Queue delayQueue(){
        return  new Queue(DELAY_QUEUE_NAME, true);
    }

    @Bean
    public Binding delayBinding(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_ROUTING_KEY).noargs();
    }
}
