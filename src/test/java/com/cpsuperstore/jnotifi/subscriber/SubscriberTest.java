package com.cpsuperstore.jnotifi.subscriber;

import com.cpsuperstore.jnotifi.Common;
import com.cpsuperstore.jnotifi.publisher.PublisherTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SubscriberTest {
    private final String CLIENT_ID = Common.getSubscriber("clientId");
    private final String CLIENT_SECRET = Common.getSubscriber("clientSecret");

    @Test
    void receiveMessage() {
        int messageCount = new PublisherTest().publishTestMessages();

        Subscriber subscriber = new Subscriber(CLIENT_ID, CLIENT_SECRET);
        Message[] messages = subscriber.pollMessages();

        assertEquals(messageCount, messages.length);

        for (Message message : messages){
            message.confirmMessage();
            assertTrue(message.isConfirmed());
        }
    }
}
