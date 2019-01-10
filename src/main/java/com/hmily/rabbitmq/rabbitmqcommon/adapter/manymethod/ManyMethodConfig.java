package com.hmily.rabbitmq.rabbitmqcommon.adapter.manymethod;

import java.util.HashMap;
import java.util.Map;
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

import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultConfig;

@Configuration
public class ManyMethodConfig extends DefaultConfig {

	public static final String MANY_METHOD_QUEUE1_NAME = "test.adapter.many.method1";
	public static final String MANY_METHOD_QUEUE2_NAME = "test.adapter.many.method2";
	public static final String MANY_METHOD_ROUTING_KEY1 = "adapter.many.method1";
	public static final String MANY_METHOD_ROUTING_KEY2 = "adapter.many.method2";

//    声明 队列
	@Bean
	public Queue manyMethodQueue1() {
		return new Queue(MANY_METHOD_QUEUE1_NAME, true); // 队列持久
	}

//    交换机和队列绑定
	@Bean
	public Binding bindingManyMethod1() {
		return BindingBuilder.bind(manyMethodQueue1()).to(super.adapterExchange()).with(MANY_METHOD_ROUTING_KEY1);
	}
	
	@Bean
	public Queue manyMethodQueue2() {
		return new Queue(MANY_METHOD_QUEUE2_NAME, true); // 队列持久
	}
	@Bean
	public Binding bindingManyMethod2() {
		return BindingBuilder.bind(manyMethodQueue2()).to(super.adapterExchange()).with(MANY_METHOD_ROUTING_KEY2);
	}

	@Bean
	public SimpleMessageListenerContainer myMethodMessageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(manyMethodQueue1(), manyMethodQueue2()); // 监听的队列
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
		// 队列名称 和 方法名称 也可以进行一一的匹配
		MessageListenerAdapter adapter = new MessageListenerAdapter(new ManyMethodMsgDelegate());
		adapter.setMessageConverter(new TextMessageConverter());
		Map<String, String> queueOrTagToMethodName = new HashMap<>();
		queueOrTagToMethodName.put(MANY_METHOD_QUEUE1_NAME, "method1");
		queueOrTagToMethodName.put(MANY_METHOD_QUEUE2_NAME, "method2");
		adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
		container.setMessageListener(adapter);
		return container;
	}

}
