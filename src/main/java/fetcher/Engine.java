package fetcher;

import com.fasterxml.jackson.databind.JsonNode;
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


    public ProductData findProductData(String barcode) throws IOException, CredentialsDayLimitException {

        fetchSearchResultAsJson(barcode);

        JsonNode responseRootNode = JsonHandler.jsonStringToNode(jsonSearchResult);


        try {
            String photoURL = Parser.parsePhotoURL(responseRootNode);
            String userRate = Parser.parseUserRate(responseRootNode);
            String description = fetchProductDescription(responseRootNode);
            return new ProductData(photoURL, userRate, description);
        }   catch (NullPointerException e) {
            return new ProductData(getNotFoundDescription(barcode));
        }



    }
    private String getNotFoundDescription(String barcode) {
        return String.format("К сожалению, продукт со штрихкодом %s не был найден! Пожалуйста, проверьте совпадают ли указанные цифры с кодом, указанным на упаковке и попробуйте еще раз. Товары, не сертифицированные на территории РФ, могут быть не найдены. ", barcode);
    }

    public void setCredentials(Credentials credentials) {

        this.credentials = credentials;
        initTargetURL();
    }


    private String fetchProductDescription(JsonNode responseRootNode) throws IOException {
        String productURL = Parser.parseProductURL(responseRootNode);
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
