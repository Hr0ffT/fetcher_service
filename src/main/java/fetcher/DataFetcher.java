package fetcher;

import org.json.JSONException;
import util.ProductData;
import util.jsonhandler.JsonHandler;
import java.io.IOException;


public class DataFetcher {

    private final FetcherLogic fetcherLogic;
    private final FetcherEngine fetcherEngine;
    EngineCredentials credentials;

    public DataFetcher() {
        this.credentials = new EngineCredentials();
        this.fetcherEngine = new FetcherEngine(credentials);
        this.fetcherLogic = new FetcherLogic(this);


    }


    public String fetchProductDataAsJson(String query) throws IOException, NoMoreAvailableCredentialsException, JSONException {

        ProductData productData = fetcherLogic.startSearch(query);

        return JsonHandler.serializeToJson(productData);

    }


    public FetcherEngine getFetcherEngine() {
        return fetcherEngine;
    }

    public void switchEngineCredentials() {
        fetcherEngine.setCredentials(new EngineCredentials());
    }


}
