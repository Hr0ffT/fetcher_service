package fetcher;

import org.json.JSONException;
import org.jsoup.Jsoup;
import util.Parser;
import util.ProductData;

import java.io.IOException;

public class FetcherEngine {


    private String targetURL;
    private EngineCredentials credentials;


    public FetcherEngine(EngineCredentials credentials) {
        this.credentials = credentials;
        initTargetURL();
    }


    public ProductData findProductData(String barcode) throws IOException, JSONException {
        String jsonSearchResult = fetchSearchResultAsJson(barcode);
        return Parser.parseProductDataFromJson(jsonSearchResult);
    }

    public void setCredentials(EngineCredentials credentials) {
        this.credentials = credentials;
        initTargetURL();
    }




    private String fetchSearchResultAsJson(String query) throws IOException {
        return Jsoup.connect(targetURL + query).ignoreContentType(true).execute().body();
    }

    private void initTargetURL() {
        targetURL = String.format("https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=",
                this.credentials.getApiKey(), this.credentials.getCX());
    }




}
