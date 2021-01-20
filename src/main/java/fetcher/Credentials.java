package fetcher;

import util.JsonHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class Credentials {

    String cx;
    String apiKey;

    private static URL credentialsJsonURL;
    private static Map<String, String> parametersPool;

    private static int credentialsUsed = 0;

    public Credentials() {
        initCredentialsURL();
        if (parametersPool == null) {
            initParametersMap();
        }
        initFields();
        checkAvailableCredentials();
    }

    private void initCredentialsURL() {
        String fileIDSysEnvName = "CredentialsFileID";

        String credentialsFileID = System.getenv(fileIDSysEnvName);
        try {
            credentialsJsonURL = new URL(String.format("https://drive.google.com/u/0/uc?id=%s&export=download", credentialsFileID));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }



    private void initParametersMap() {
        try {
            parametersPool = JsonHandler.jsonFileToMap(credentialsJsonURL);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void initFields() {

        Iterator<Map.Entry<String, String>> iterator = parametersPool.entrySet().iterator();
        Map.Entry<String, String> entry;


        if (credentialsUsed < parametersPool.size() + 1) {
            System.out.println("Есть доступные варианты");
            for (int i = 0; i <= credentialsUsed; i++) {
                entry = iterator.next();
                System.out.println("ENTRY " + entry);
                this.cx = entry.getKey();
                this.apiKey = entry.getValue();
            }


            System.out.println("Initializing new credentials: " + cx + " " + apiKey);
            checkAvailableCredentials();
            credentialsUsed++;

        } else {
            checkAvailableCredentials();
            System.out.println("No more available engines!!");
        }

    }

    public static void resetUsedCredentials() {
        credentialsUsed = 0;
        System.out.println("Resetting used credentials");

    }

    public static void checkAvailableCredentials() {
        Logic.thereAreAvailableCredentials = credentialsUsed < poolSize() + 1;
    }


    public String getApiKey() {
        return apiKey;
    }

    public String getCX() {
        return cx;
    }

    public static int poolSize() {
        return parametersPool.size();
    }


}
