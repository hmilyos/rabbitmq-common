package com.hmily.rabbitmq.rabbitmqcommon.adapter.mymethod;

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
public class MyMethodConfig extends DefaultConfig {

	public static final String MY_METHOS_QUEUE_NAME = "test.adapter.my.method";
	public static final String MY_METHOS_ROUTING_KEY = "adapter.my.method";

//    声明 队列
	@Bean
	public Queue myMethodQueue() {
		return new Queue(MY_METHOS_QUEUE_NAME, true); // 队列持久
	}

//    交换机和队列绑定
	@Bean
	public Binding bindingMyMethod() {
		return BindingBuilder.bind(myMethodQueue()).to(super.adapterExchange()).with(MY_METHOS_ROUTING_KEY);
	}

	@Bean
	public SimpleMessageListenerContainer myMethodMessageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(myMethodQueue()); // 监听的队列
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
		// 1.2 适配器方式. 可以自己指定一个方法的名字: consumeMessage
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MyMethodMsgDelegate());
		adapter.setDefaultListenerMethod("consumeMessage");
		container.setMessageListener(adapter);
		return container;
	}

}
