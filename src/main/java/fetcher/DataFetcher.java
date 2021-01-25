package fetcher;

import exceptions.ProductNotFoundException;
import exceptions.ServiceUnavailableException;
import org.apache.log4j.Logger;
import util.ProductData;


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


    public ProductData fetchProductData(String query) throws ProductNotFoundException, ServiceUnavailableException {

        return logic.startSearch(query);
    }


    public Engine getFetcherEngine() {
        return engine;
    }

    public void switchEngineCredentials() {
        log.info("Switching credentials");
        engine.setCredentials(new Credentials());
    }


}
