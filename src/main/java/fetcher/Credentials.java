package fetcher;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Credentials {

    String cx;
    String apiKey;
    private static Map<String, String> parametersPool;

    private static int credentialsUsed = 0;

    public Credentials() {
        if (parametersPool == null) {
            initParametersMap();
        }
        initFields();
        checkAvailableCredentials();
    }


    private void putParameters() {

        parametersPool.put(
                "b16555d6478b893e4", "AIzaSyAu25Ye0uxJEjJcLvIZj8YZJF1a82vdInQ"
        );
        parametersPool.put(
                "6ff535218ad7fe3ba", "AIzaSyC8KNeuOz61wQlj6202MYO3piEbSDdw9rc"
        );
        parametersPool.put(
                "b5931624bb8243dd1", "AIzaSyAWtl7WJNi_Kyw2QW_DZPTlfawEaMFaDhI"
        );


    }


    private void initParametersMap() {
        parametersPool = new LinkedHashMap<>();
        putParameters();
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
