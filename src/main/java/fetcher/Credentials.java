package fetcher;

import org.apache.log4j.Logger;
import util.JsonHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;


public class Credentials {

    private static final Logger log = Logger.getLogger(Credentials.class);

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
            log.error(e);
        }

    }


    private void initParametersMap() {
        try {
            parametersPool = JsonHandler.jsonFileToMap(credentialsJsonURL);
        } catch (IOException e) {
            log.error(e);
        }

    }

    private void initFields() {

        Iterator<Map.Entry<String, String>> iterator = parametersPool.entrySet().iterator();
        Map.Entry<String, String> entry;


        if (credentialsUsed < parametersPool.size() + 1) {

            for (int i = 0; i <= credentialsUsed; i++) {
                entry = iterator.next();
                log.info("Using creds: " + entry);
                this.cx = entry.getKey();
                this.apiKey = entry.getValue();
            }

            log.info(String.format("Credentials initialization: %s %s", cx, apiKey));

            checkAvailableCredentials();
            credentialsUsed++;

        } else {
            checkAvailableCredentials();
            log.warn("No more available engines!");
        }

    }

    public static void resetUsedCredentials() {
        credentialsUsed = 0;
        log.info("Resetting used credentials");
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
