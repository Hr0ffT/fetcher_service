package fetcher;

import org.apache.log4j.Logger;
import org.json.JSONException;
import util.JsonHandler;
import util.ProductData;

import java.io.IOException;


public class DataFetcher {

    private static final Logger log = Logger.getLogger(DataFetcher.class);

    private final Logic logic;
    private final Engine engine;
    Credentials credentials;

    public DataFetcher() {
        this.credentials = new Credentials();
        this.engine = new Engine(credentials);
        this.logic = new Logic(this);
    }


    public String fetchProductDataAsJson(String query) throws IOException, CredentialsDayLimitException, JSONException {
        ProductData productData = logic.startSearch(query);
        return JsonHandler.serializeToJson(productData);
    }


    public Engine getFetcherEngine() {
        return engine;
    }

    public void switchEngineCredentials() {
        log.info("Switching credentials");
        engine.setCredentials(new Credentials());
    }


}
