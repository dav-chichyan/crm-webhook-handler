package maqoor.webhook.handler.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import maqoor.webhook.handler.config.MyRabbitConfiguration;
import maqoor.webhook.handler.exception.MessageDeliveryException;
import maqoor.webhook.handler.service.MakichyanDeliveryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class MakichyanListener {

    @Autowired
    public MakichyanDeliveryService makichyanDeliveryService;

    @RabbitListener(queues = MyRabbitConfiguration.MAKICHYAN_QUEUE, ackMode = "MANUAL")
    public void handleMakichyan(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        makichyanDeliveryService.send(message, channel, tag);
    }

}
