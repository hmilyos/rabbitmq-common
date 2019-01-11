package com.hmily.rabbitmq.rabbitmqcommon.adapter.json;

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
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultConfig;

@Configuration
public class JsonConfig extends DefaultConfig {

	public static final String JSON_QUEUE_NAME = "test.adapter.json";
	public static final String JSON_ROUTING_KEY = "adapter.json";

//    声明 队列
	@Bean
	public Queue jsonQueue() {
		return new Queue(JSON_QUEUE_NAME, true); // 队列持久
	}

//    交换机和队列绑定
	@Bean
	public Binding bindingJsonQueue() {
		return BindingBuilder.bind(jsonQueue()).to(super.adapterExchange()).with(JSON_ROUTING_KEY);
	}
	
	@Bean
	public SimpleMessageListenerContainer jsonMessageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(jsonQueue()); // 监听的队列
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
        // json格式的转换器
      MessageListenerAdapter adapter = new MessageListenerAdapter(new JsonMsgDelegate());
      adapter.setDefaultListenerMethod("consumeMessage");
      Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
      adapter.setMessageConverter(jackson2JsonMessageConverter);
      container.setMessageListener(adapter);
      return container;
	}

}
