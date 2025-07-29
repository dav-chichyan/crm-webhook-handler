package maqoor.webhook.handler.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import maqoor.webhook.handler.exception.MessageDeliveryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@Service
@Slf4j
public class MakichyanDeliveryService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${makinchyan.consulting.url}")
    private String CONSULTING_URL;

    @Retryable(
            maxAttempts = 10,
            backoff = @Backoff(delay = 3000, multiplier = 2),
            retryFor = MessageDeliveryException.class
    )
    public void send(String message, Channel channel, long tag) {
        log.info("Webhook received by Makichyan: {}", message);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(message, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(CONSULTING_URL, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Makichyan Send Success {}", request);
                channel.basicAck(tag, false);
            } else {
                throw new RuntimeException("Request failed with the status : " + response.getStatusCode().value());
            }
        } catch (Exception e) {
            log.info("Makichyan Send Failed {}", e.getMessage());
            throw new MessageDeliveryException(channel, message, tag, "Makichyan Send Failed", e);
        }
    }


    @Recover
    private void recover(MessageDeliveryException e) throws IOException {
        log.error("Makichyan Max Attempt exceeded {}", e.getMessage());
        e.getChannel().basicReject(e.getTag(), false);
    }
}
