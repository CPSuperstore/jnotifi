import com.cpsuperstore.jnotifi.PublicationResponse;
import com.cpsuperstore.jnotifi.Publisher;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PublisherTest {
    private final String CLIENT_ID = "fe1eb507-4246-4adb-aa58-a384d4e08625";
    private final String CLIENT_SECRET = "f0671494-dea1-11eb-ae30-0a7e483b6057";

    @Test
    void sendMessage() {
        Publisher publisher = new Publisher(CLIENT_ID, CLIENT_SECRET);
        PublicationResponse response = publisher.publish("From Java", "dev");
        System.out.println(response);
    }
}
