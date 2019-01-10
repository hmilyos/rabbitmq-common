package com.hmily.rabbitmq.rabbitmqcommon.adapter.str;

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
public class StringConfig extends DefaultConfig {

	public static final String STRING_QUEUE_NAME = "test.adapter.string";
	public static final String STRING_ROUTING_KEY = "adapter.string";

//    声明 队列
	@Bean
	public Queue stringQueue() {
		return new Queue(STRING_QUEUE_NAME, true); // 队列持久
	}

//    交换机和队列绑定
	@Bean
	public Binding bindingString() {
		return BindingBuilder.bind(stringQueue()).to(super.adapterExchange()).with(STRING_ROUTING_KEY);
	}

	@Bean
	public SimpleMessageListenerContainer stringMessageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(stringQueue()); // 监听的队列
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
        //  从字节数组转换为String
    	MessageListenerAdapter adapter = new MessageListenerAdapter(new StringMsgDelegate());
    	adapter.setDefaultListenerMethod("consumeMessage");
    	adapter.setMessageConverter(new TextMessageConverter());
    	container.setMessageListener(adapter);
        return container;
	}

}
