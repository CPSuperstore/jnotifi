import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Common {
    private static Gson gson = new Gson();
    private static final String CREDENTIALS_FILE = "credentials.json";

    private static Map<String, Map<String, String>> credentials;

    private static void ensureConfigIsLoaded(){
        if (credentials != null){
            return;
        }

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
