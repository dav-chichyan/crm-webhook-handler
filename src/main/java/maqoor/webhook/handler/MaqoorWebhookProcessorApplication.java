package maqoor.webhook.handler;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRabbit
@EnableRetry(proxyTargetClass = true)
public class MaqoorWebhookProcessorApplication {
	public static void main(String[] args) {
		SpringApplication.run(MaqoorWebhookProcessorApplication.class, args);

	}

}
