package com.cpsuperstore.jnotifi.publisher;

import com.cpsuperstore.jnotifi.Common;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class PublisherTest {
    private final String CLIENT_ID = Common.getPublisher("clientId");
    private final String CLIENT_SECRET = Common.getPublisher("clientSecret");
    private final String CATEGORY = Common.getPublisher("category");

    @Test
    void singleCategory() {
        Publisher publisher = new Publisher(CLIENT_ID, CLIENT_SECRET);
        PublicationResponse response = publisher.publish(Common.MESSAGE, CATEGORY);

        testResponse(response);
    }

    @Test
    void arrayCategory() {
        Publisher publisher = new Publisher(CLIENT_ID, CLIENT_SECRET);
        PublicationResponse response = publisher.publish(Common.MESSAGE, new String[] {CATEGORY});

        testResponse(response);
    }

    @Test
    void listCategory() {
        Publisher publisher = new Publisher(CLIENT_ID, CLIENT_SECRET);
        PublicationResponse response = publisher.publish(Common.MESSAGE, Collections.singletonList(CATEGORY));

        testResponse(response);
    }

    private void testResponse(PublicationResponse response){
        assertEquals(1, response.getSuccess());
        assertEquals(0, response.getFailed());
        assertEquals(1, response.getTotal());
    }

    public int publishTestMessages(){
        singleCategory();
        arrayCategory();
        listCategory();

        return 3;
    }
}