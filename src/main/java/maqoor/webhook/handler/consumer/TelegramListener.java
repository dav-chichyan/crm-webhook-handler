package maqoor.webhook.handler.consumer;

import com.rabbitmq.client.Channel;
import maqoor.webhook.handler.config.MyRabbitConfiguration;
import maqoor.webhook.handler.service.TelegramDeliveryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class TelegramListener {

    @Autowired
    private TelegramDeliveryService telegramDeliveryService;

    @RabbitListener(queues = MyRabbitConfiguration.TELEGRAM_QUEUE, ackMode = "MANUAL")
    public void handleTelegram(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        telegramDeliveryService.send(message, channel, tag);
    }
}
