package fetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

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

        //                System.out.println(jsonSearchResult); //печать боди

        return parseProductDataFromJson(jsonSearchResult);
    }

    private String fetchSearchResultAsJson(String query) throws IOException {
        return Jsoup.connect(targetURL + query).ignoreContentType(true).execute().body();
    }

    private ProductData parseProductDataFromJson(String json) throws JSONException {

        String productName;
        String photoURL;
        String userRate;
        String description;

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArrayItems = jsonObject.getJSONArray("items");

        description = jsonArrayItems.getJSONObject(0).getString("snippet").replaceAll("\\n", "");
        photoURL = jsonArrayItems.getJSONObject(0).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:image").replaceAll("\\n", "");
        userRate = jsonArrayItems.getJSONObject(0).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:description").replaceAll("\\n", "");
        productName = jsonArrayItems.getJSONObject(0).getString("title").replaceAll("\\.", "");

        if (!isCorrectUserRate(userRate)) {
            userRate = " ";
        }

        return new ProductData(productName, photoURL, userRate, description);

    }

    private void initTargetURL() {
        targetURL = String.format("https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=",
                this.credentials.getApiKey(), this.credentials.getCX());
    }

    public void setCredentials(EngineCredentials credentials) {
        this.credentials = credentials;
        initTargetURL();
    }

    private boolean isCorrectUserRate(String userRate) {
        return userRate.contains("★");
    }

}
