package fetcher;

import com.fasterxml.jackson.databind.JsonNode;
import exceptions.CredentialsDayLimitException;
import exceptions.ProductNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.JsonHandler;
import util.Parser;
import util.ProductData;

import java.io.IOException;


public class Engine {

    private String targetURL;
    private Credentials credentials;
    private String jsonSearchResult;


    public Engine(Credentials credentials) {
        this.credentials = credentials;
        initTargetURL();
    }


    public ProductData findProductData(String barcode) throws CredentialsDayLimitException, ProductNotFoundException, IOException {

        fetchSearchResultAsJson(barcode);

        JsonNode responseRootNode = JsonHandler.jsonStringToNode(jsonSearchResult);


        try {

            String photoURL = Parser.getTargetValueFromResponse(responseRootNode, Parser.Fields.PHOTO_URL);
            String userRate = Parser.getTargetValueFromResponse(responseRootNode, Parser.Fields.USER_RATE);
            String description = fetchProductDescription(responseRootNode);
            return new ProductData(photoURL, userRate, description);
        } catch (NullPointerException e) {
            throw new ProductNotFoundException();
        }


    }

    public void setCredentials(Credentials credentials) {

        this.credentials = credentials;
        initTargetURL();
    }


    private String fetchProductDescription(JsonNode responseRootNode) throws IOException {

        String productURL = Parser.getTargetValueFromResponse(responseRootNode, Parser.Fields.PRODUCT_URL);
        Document htmlDoc = Jsoup.connect(productURL).get();

        String name = htmlDoc.select("h1").text();

        Elements descriptionFields = htmlDoc.select("div._3Ccd");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" ").append("\n").append(" ");
        for (Element descriptionField : descriptionFields) {
            stringBuilder.append(descriptionField.text()).append(", ");
        }

        return stringBuilder.toString();
    }


    private void fetchSearchResultAsJson(String query) throws CredentialsDayLimitException {

        try {
            jsonSearchResult = Jsoup.connect(targetURL + query).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            throw new CredentialsDayLimitException();
        }

    }

    private void initTargetURL() {
        targetURL = String.format("https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=",
                this.credentials.getApiKey(), this.credentials.getCX());
    }


}
