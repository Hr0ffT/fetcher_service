import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class EngineCredentials {

    String cx;
    String apiKey;
    private static Map<String, String> parametersPool;

    private static int credentialsUsed = 0;

    public EngineCredentials() {
        System.out.println("SearchEngineParameters init");

        initParametersMap();
        initFields();

        checkAvailableCredentials();

        //        System.out.println(searchersCounter);
    }

    private void putParameters() {

        parametersPool.put(
                "b16555d6478b893e4", "AIzaSyAu25Ye0uxJEjJcLvIZj8YZJF1a82vdInQ"
        );
        //        parametersPool.put(
        //                "6ff535218ad7fe3ba", "AIzaSyC8KNeuOz61wQlj6202MYO3piEbSDdw9rc"
        //        );

    }

    private void initParametersMap() {
        parametersPool = new LinkedHashMap<>();
        putParameters();
    }

    private void initFields() {

        Iterator<Map.Entry<String, String>> iterator = parametersPool.entrySet().iterator();
        Map.Entry<String, String> entry = iterator.next();


        if (credentialsUsed < parametersPool.size()) {
            for (int i = 1; i <= credentialsUsed; i++) {
                entry = iterator.next();
            }

            this.cx = entry.getKey();
            this.apiKey = entry.getValue();

            credentialsUsed++;

        } else {
            checkAvailableCredentials();
            System.out.println("No more available engines!!");
        }

    }

    public static void resetUsedCredentials() {
        credentialsUsed = 0;
        checkAvailableCredentials();
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

    public static void checkAvailableCredentials() {
        FetcherLogic.thereAreAvailableCredentials = credentialsUsed < poolSize();
    }

}
