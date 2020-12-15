import org.json.JSONException;

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

    public String fetch(String query) throws IOException, NoMoreAvailableCredentialsException, JSONException {

        String jsonProductData = null;

        ProductData productData = fetcherLogic.startSearch(query);


        //convert data to json

        //       ObjectMapper objectMapper = new ObjectMapper();
        //       objectMapper.writeValueAsString()



        return jsonProductData;
    }


    public FetcherEngine getSearchEngine() {
        return fetcherEngine;
    }

    public void switchEngineCredentials() {
        fetcherEngine.setCredentials(new EngineCredentials());
    }
}
