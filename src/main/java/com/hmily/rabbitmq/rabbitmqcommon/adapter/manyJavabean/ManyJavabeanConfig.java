package com.hmily.rabbitmq.rabbitmqcommon.adapter.manyJavabean;

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
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultConfig;

@Configuration
public class ManyJavabeanConfig extends DefaultConfig {

	public static final String MANY_JAVA_QUEUE_NAME = "test.adapter.many.java.bean";
	public static final String MANY_JAVA_ROUTING_KEY = "adapter.many.java.bean";

//    声明 队列
	@Bean
	public Queue manyJavaQueue() {
		return new Queue(MANY_JAVA_QUEUE_NAME, true); // 队列持久
	}

//    交换机和队列绑定
	@Bean
	public Binding bindingManyJavaQueue() {
		return BindingBuilder.bind(manyJavaQueue()).to(super.adapterExchange()).with(MANY_JAVA_ROUTING_KEY);
	}

	@Bean
	public SimpleMessageListenerContainer manyJavaBeanMessageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(manyJavaQueue()); // 监听的队列
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
        // 5 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换
      MessageListenerAdapter adapter = new MessageListenerAdapter(new ManyJavaBeanMsgDelegate());
      adapter.setDefaultListenerMethod("consumeMessage");
      Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
      DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();

      Map<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
      idClassMapping.put("order", com.hmily.rabbitmq.rabbitmqcommon.entity.Order.class);
      idClassMapping.put("messageFailed", com.hmily.rabbitmq.rabbitmqcommon.entity.MessageFailed.class);

      javaTypeMapper.setIdClassMapping(idClassMapping);

      jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
      adapter.setMessageConverter(jackson2JsonMessageConverter);
      container.setMessageListener(adapter);
      return container;
	}

}
