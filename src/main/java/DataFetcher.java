import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper objectMapper = new ObjectMapper();

        ProductData productData = fetcherLogic.startSearch(query);

        return objectMapper.writeValueAsString(productData);

    }


    public FetcherEngine getFetcherEngine() {
        return fetcherEngine;
    }

    public void switchEngineCredentials() {
        fetcherEngine.setCredentials(new EngineCredentials());
    }
}
