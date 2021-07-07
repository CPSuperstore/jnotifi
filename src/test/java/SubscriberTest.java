import com.cpsuperstore.jnotifi.subscriber.Message;
import com.cpsuperstore.jnotifi.subscriber.Subscriber;
import org.junit.jupiter.api.Test;

public class SubscriberTest {
    private final String CLIENT_ID = Common.getSubscriber("clientId");
    private final String CLIENT_SECRET = Common.getSubscriber("clientSecret");

    @Test
    void receiveMessage() {
        Subscriber subscriber = new Subscriber(CLIENT_ID, CLIENT_SECRET);
        Message[] messages = subscriber.pollMessages();

        for (Message message : messages){
            System.out.println(message);
            message.confirmMessage();
        }
    }
}
