package com.cpsuperstore.jnotifi;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Common {
    private static Gson gson = new Gson();
    private static final String CREDENTIALS_FILE = "credentials.json";
    private static final String DEFAULT_CREDENTIALS = "{\n" +
            "  \"publisher\": {\n" +
            "    \"clientId\": \"\",\n" +
            "    \"clientSecret\": \"\",\n" +
            "    \"category\": \"\"\n" +
            "  },\n" +
            "  \"subscriber\": {\n" +
            "    \"clientId\": \"\",\n" +
            "    \"clientSecret\": \"\"\n" +
            "  }\n" +
            "}\n";

    public static final String MESSAGE = "jnotifi @ " + (System.currentTimeMillis() / 1000L);

    private static Map<String, Map<String, String>> credentials;

    private static void ensureConfigIsLoaded(){
        if (credentials != null){
            return;
        }

        ensureConfigFileExists();

        try {
            File myObj = new File(CREDENTIALS_FILE);
            Scanner myReader = new Scanner(myObj);

            StringBuilder data = new StringBuilder();
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
            }

            credentials = gson.fromJson(data.toString(), Map.class);
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static boolean ensureConfigFileExists(){
        try {
            File myObj = new File(CREDENTIALS_FILE);
            if (myObj.createNewFile()) {

                FileWriter myWriter = new FileWriter(CREDENTIALS_FILE);
                myWriter.write(DEFAULT_CREDENTIALS);
                myWriter.close();

                System.err.println("Could not execute test cases as credentials.json file could not be found");
                System.err.println("Created empty credentials file at " + myObj.getAbsolutePath());
                System.err.println("You can get subscriber credentials at " + Constants.URL_BASE + "/notifi/subscribers/device");
                System.err.println("You can get publisher credentials at " + Constants.URL_BASE + "/notifi/publishers");
                System.err.println("You can mange categories at " + Constants.URL_BASE + "/notifi/categories");

                System.err.println("\nTerminating execution");
                System.exit(1);

                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Map<String, String> getCredentialType(String type) {
        ensureConfigIsLoaded();
        return credentials.get(type);
    }

    public static String getPublisher(String type) {
        return getCredentialType("publisher").get(type);
    }

    public static String getSubscriber(String type) {
        return getCredentialType("subscriber").get(type);
    }
}
