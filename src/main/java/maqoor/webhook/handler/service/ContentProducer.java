package maqoor.webhook.handler.service;


import lombok.extern.slf4j.Slf4j;
import maqoor.webhook.handler.config.MyRabbitConfiguration;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContentProducer {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void publishWebhook(String content) {
        log.info("Publishing to OrderQueue: {}", MyRabbitConfiguration.TOPIC_EXCHANGE);
        try {
            rabbitTemplate.convertAndSend(
                    MyRabbitConfiguration.TOPIC_EXCHANGE,
                    "webhook.created",
                    content,
                    content1 -> {
                        content1.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        return content1;
                    }
            );
        } catch (Exception e) {
            log.error("Error publishing to OrderQueue", e);
        }
    }
}
