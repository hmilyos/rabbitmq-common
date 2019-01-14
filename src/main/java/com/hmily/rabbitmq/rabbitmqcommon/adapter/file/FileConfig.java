package com.hmily.rabbitmq.rabbitmqcommon.adapter.file;

import com.hmily.rabbitmq.rabbitmqcommon.adapter.defaultt.DefaultConfig;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.file.converter.ImageMessageConverter;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.file.converter.PDFMessageConverter;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.javabean.JavaBeanMsgDelegate;
import com.hmily.rabbitmq.rabbitmqcommon.adapter.str.TextMessageConverter;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class FileConfig extends DefaultConfig {

	public static final String IMAGE_FILE_QUEUE_NAME = "test.adapter.file.image";
	public static final String IMAGE_FILE_ROUTING_KEY = "adapter.file.image";
    public static final String PDF_FILE_QUEUE_NAME = "test.adapter.file.pdf";
    public static final String PDF_FILE_ROUTING_KEY = "adapter.file.pdf";

    //    声明 队列
    @Bean
    public Queue imageFileQueue() {
        return new Queue(IMAGE_FILE_QUEUE_NAME, true); // 队列持久
    }

    //    交换机和队列绑定
    @Bean
    public Binding bindingImageFileQueue() {
        return BindingBuilder.bind(imageFileQueue()).to(super.adapterExchange()).with(IMAGE_FILE_ROUTING_KEY);
    }
    //    声明 队列
    @Bean
    public Queue filePDFQueue() {
        return new Queue(PDF_FILE_QUEUE_NAME, true); // 队列持久
    }

    //    交换机和队列绑定
    @Bean
    public Binding bindingPDFFileQueue() {
        return BindingBuilder.bind(filePDFQueue()).to(super.adapterExchange()).with(PDF_FILE_ROUTING_KEY);
    }

	@Bean
	public SimpleMessageListenerContainer fileMessageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(imageFileQueue(), filePDFQueue()); // 监听的队列
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

        // 6 ext convert
        MessageListenerAdapter adapter = new MessageListenerAdapter(new FileMessageDelegate());
        adapter.setDefaultListenerMethod("consumeMessage");

        //全局的转换器:
        ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();

        TextMessageConverter textConvert = new TextMessageConverter();
        convert.addDelegate("text", textConvert);
        convert.addDelegate("html/text", textConvert);
        convert.addDelegate("xml/text", textConvert);
        convert.addDelegate("text/plain", textConvert);

        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
        convert.addDelegate("json", jsonConvert);
        convert.addDelegate("application/json", jsonConvert);

        ImageMessageConverter imageConverter = new ImageMessageConverter();
        convert.addDelegate("image/png", imageConverter);
        convert.addDelegate("image", imageConverter);

        PDFMessageConverter pdfConverter = new PDFMessageConverter();
        convert.addDelegate("application/pdf", pdfConverter);


        adapter.setMessageConverter(convert);
        container.setMessageListener(adapter);
		return container;
	}

}
