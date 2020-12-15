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


    public ProductData findProductData(String barcode) throws IOException {
        String jsonSearchResult = fetchSearchResultAsJson(barcode);
        return parseProductDataFromJson(jsonSearchResult);
    }

    private String fetchSearchResultAsJson(String query) throws IOException {

            return Jsoup.connect(targetURL + query).ignoreContentType(true).execute().body();

    }

    static ProductData parseProductDataFromJson(String json) {

        ProductData productData = null;

        String productName;
        String manufacturer;
        String country;
        String photoURL;
        String userRate;

        try {


            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArrayItems = jsonObject.getJSONArray("items");

            String snippet = jsonArrayItems.getJSONObject(0).getString("snippet").replaceAll("\\n", "");
            photoURL = jsonArrayItems.getJSONObject(0).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:image").replaceAll("\\n", "");

            int manufacturerIndex = snippet.lastIndexOf("Производитель. ") + 15;
            int countryIndex = snippet.indexOf(" Страна.");
            int storageCond = snippet.indexOf(" Условия хранения.");

            productName = jsonArrayItems.getJSONObject(0).getString("title").replaceAll("\\.", "");
            manufacturer = snippet.substring(manufacturerIndex, countryIndex);
            country = snippet.substring(countryIndex + 9, storageCond);

            System.out.println(productName);
            System.out.println(manufacturer);
            System.out.println(country);
            System.out.println(photoURL);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productData;

    }

    private void initTargetURL() {
        targetURL = String.format("https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=",
                this.credentials.getApiKey(), this.credentials.getCX());
    }

    public void setCredentials(EngineCredentials credentials) {
        this.credentials = credentials;
        initTargetURL();
    }

}
