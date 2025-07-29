package maqoor.webhook.handler.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyRabbitConfiguration {


    public static final String TOPIC_EXCHANGE = "webhook_topic_exchange";

    public static final String MAKICHYAN_QUEUE = "makichyan_queue";

    public static final String TELEGRAM_QUEUE = "telegram_queue";

    @Bean
    public Queue makichyanQueue() {
        return new Queue(MAKICHYAN_QUEUE, true);
    }

    @Bean
    public Queue telegramQueue() {
        return new Queue(TELEGRAM_QUEUE, true);
    }

    @Bean
    public Binding makichyanBinding() {
        return BindingBuilder
                .bind(makichyanQueue())
                .to(topicExchange())
                .with("webhook.created");
    }

    @Bean
    public Binding telegramBinding() {
        return BindingBuilder
                .bind(telegramQueue())
                .to(topicExchange())
                .with("webhook.created");
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
