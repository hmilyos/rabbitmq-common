package com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt;

import java.util.UUID;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DefaultConfig {
	
	public static final String EXCHANEGE_NAME = "test_adapter";
	public static final String DEFAULT_QUEUE_NAME = "test.adapter.default";
	public static final String DEFAULT_ROUTING_KEY = "adapter.default";
	
    @Bean
    public DirectExchange adapterExchange() {
//    	是否持久化，是否自动删除
		return new DirectExchange(EXCHANEGE_NAME, true, false);
	}
    
//    声明  队列
    @Bean
    public Queue defaultQueue() {
    	return new Queue(DEFAULT_QUEUE_NAME, true); //队列持久
    }
    
//    将上面的交换机和队列绑定
    @Bean
    public Binding bindingDefaultQueue() {
    	return BindingBuilder.bind(defaultQueue()).to(adapterExchange()).with(DEFAULT_ROUTING_KEY);
    }

	@Bean
	public SimpleMessageListenerContainer defaultMessageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(defaultQueue());    //监听的队列
		container.setConcurrentConsumers(1); // 当前的消费者数量
		container.setMaxConcurrentConsumers(5); // 最大的消费者数量
		container.setDefaultRequeueRejected(false); // 是否重回队列

		container.setAcknowledgeMode(AcknowledgeMode.AUTO); // 签收模式
		container.setExposeListenerChannel(true);
		container.setConsumerTagStrategy(new ConsumerTagStrategy() { // 消费端的标签策略
			@Override
			public String createConsumerTag(String queue) {
				return queue + "_" + UUID.randomUUID().toString();
			}
		});
//      1.1 适配器方式. 默认是有自己的方法名字的：handleMessage
		MessageListenerAdapter adapter = new MessageListenerAdapter(new DefaultMsgDelegate());
		container.setMessageListener(adapter);
		return container;
	}
	
	

}
