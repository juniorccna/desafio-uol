package br.com.uol.project.first.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public static String QUEUE_NAME;
	
	public static String EXCHANGE;
	
	public static String ROUTING_KEY;

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	
	
	@Value("${first.rabbitmq.queueName}")
	private void setQueueName(String queueName) {
		RabbitMQConfig.QUEUE_NAME = queueName;
	}
	
	@Value("${first.rabbitmq.exchange}")
	private void setExchange(String exchange) {
		RabbitMQConfig.EXCHANGE = exchange;
	}
	
	@Value("${first.rabbitmq.routingKey}")
	private void setRoutingKey(String routingKey) {
		RabbitMQConfig.ROUTING_KEY = routingKey;
	}
}
