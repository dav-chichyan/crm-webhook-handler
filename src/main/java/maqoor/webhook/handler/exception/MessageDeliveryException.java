package maqoor.webhook.handler.exception;

import com.rabbitmq.client.Channel;
import lombok.Getter;


@Getter
public class MessageDeliveryException extends RuntimeException {

    private final Channel channel;
    private final String eventMessage;
    private final long tag;

    public MessageDeliveryException(Channel channel, String eventMessage, long tag,String message , Throwable cause) {
        super(message,cause);
        this.channel = channel;
        this.eventMessage = eventMessage;
        this.tag = tag;



    }

}
