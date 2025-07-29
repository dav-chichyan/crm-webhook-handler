package maqoor.webhook.handler.cleancloud;

import lombok.extern.slf4j.Slf4j;
import maqoor.webhook.handler.service.ContentProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CleanCloudWebhookController {

    @Autowired
    private ContentProducer contentProducer;

    @PostMapping(value = "/webhook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void customerCreated(@RequestBody String content) {
        log.info("Webhook received from CleanCloud: {}", content);
        contentProducer.publishWebhook(content);
    }
}
