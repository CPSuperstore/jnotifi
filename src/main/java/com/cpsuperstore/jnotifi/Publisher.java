package com.cpsuperstore.jnotifi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publisher {
    private final String clientID;
    private final String clientSecret;
    private final Gson gson = new Gson();

    public Publisher(String clientID, String clientSecret){
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public PublicationResponse publish(String message){
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", message);

        return send(payload);
    }

    public PublicationResponse publish(String message, String category){
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", message);
        payload.put("category", category);

        return send(payload);
    }

    public PublicationResponse publish(String message, List<String> categories){
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", message);
        payload.put("category", categories);

        return send(payload);
    }

    public PublicationResponse publish(String message, String[] categories){
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", message);
        payload.put("category", categories);

        return send(payload);
    }

    private PublicationResponse send(Map<String, Object> payload){
        String json = gson.toJson(payload);

        String auth = clientID + ":" + clientSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

        try {
            URL url = new URL (Constants.API_BASE + "/notification");

            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return gson.fromJson(response.toString(), PublicationResponse.class);
            }

        } catch (MalformedURLException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
