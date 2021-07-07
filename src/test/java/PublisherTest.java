import com.cpsuperstore.jnotifi.publisher.PublicationResponse;
import com.cpsuperstore.jnotifi.publisher.Publisher;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PublisherTest {
    private final String CLIENT_ID = Common.getPublisher("clientId");
    private final String CLIENT_SECRET = Common.getPublisher("clientSecret");

    @Test
    void sendMessage() {
        Publisher publisher = new Publisher(CLIENT_ID, CLIENT_SECRET);
        PublicationResponse response = publisher.publish("From Java", "dev");
        System.out.println(response);
    }
}
