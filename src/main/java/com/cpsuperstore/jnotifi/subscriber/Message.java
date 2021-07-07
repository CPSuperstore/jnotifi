package com.cpsuperstore.jnotifi.subscriber;

import com.cpsuperstore.jnotifi.Constants;
import com.cpsuperstore.jnotifi.exceptions.FailedToConfirmMessageException;
import com.cpsuperstore.jnotifi.exceptions.FailedToSubscribeException;
import com.cpsuperstore.jnotifi.publisher.PublicationResponse;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message {
    private final String id;
    private final String body;
    private final String created;

    private String clientID;
    private String clientSecret;
    private boolean confirmed = false;

    public Message(String id, String body, String created) {
        this.id = id;
        this.body = body;
        this.created = created;
    }

    public void confirmMessage(){
        Gson gson = new Gson();

        if (confirmed){
            return;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("messages", new String[] {id});
        payload.put("success", true);

        String json = gson.toJson(payload);

        String auth = clientID + ":" + clientSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

        try {
            URL url = new URL (Constants.API_BASE + "/notification");

            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            con.getInputStream();

            confirmed = true;

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            throw new FailedToConfirmMessageException("Notification confirmation endpoint not found. Try updating the jnotifi library or contact Notifi support", id);
        } catch (IOException e) {
            throw new FailedToConfirmMessageException(e.getMessage(), id);
        }
    }

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Date getCreated() {
        try {
            return dateFormat.parse(created + "-0000");
        } catch (ParseException ignored) {}
        return null;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setClientID(String clientID) {
        if (this.clientID == null) {
            this.clientID = clientID;
        } else {
            throw new RuntimeException("The Client ID can not be modified after it has been set");
        }
    }

    public void setClientSecret(String clientSecret) {
        if (this.clientSecret == null){
            this.clientSecret = clientSecret;
        } else {
            throw new RuntimeException("The Client ID can not be modified after it has been set");
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", body='" + body + '\'' +
                ", created='" + getCreated() + '\'' +
                ", confirmed=" + confirmed +
                '}';
    }
}
