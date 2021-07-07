package com.cpsuperstore.jnotifi.subscriber;

import com.cpsuperstore.jnotifi.Constants;
import com.cpsuperstore.jnotifi.exceptions.FailedToSubscribeException;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Subscriber {

    private final String clientID;
    private final String clientSecret;
    private final Gson gson = new Gson();

    public Subscriber(String clientID, String clientSecret){
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public Message[] pollMessages(){
        String auth = clientID + ":" + clientSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

        try {
            URL url = new URL (Constants.API_BASE + "/notification");

            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                Message[] messages = gson.fromJson(response.toString(), Message[].class);
                for (Message message : messages){
                    message.setClientID(clientID);
                    message.setClientSecret(clientSecret);
                }

                return messages;
            }

        } catch (MalformedURLException ignored) {

        } catch (FileNotFoundException e) {
            throw new FailedToSubscribeException("Most likely invalid credentials. Alternatively try updating the jnotifi library or contact Notifi support.");
        } catch (IOException e) {
            throw new FailedToSubscribeException(e.getMessage());
        }
        return null;
    }
}
