package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;


public class Parser {

    public static String parseUserRate(JsonNode responseRootNode) {
        String userRate = responseRootNode.get("items").get(0).get("pagemap").get("metatags").get(0).get("og:description").asText();

        if (!isCorrectUserRate(userRate)) {
            return " ";
        }

        return userRate;
    }

    public static String parseProductURL(JsonNode responseRootNode) {
        String productURLFieldName = "url";
        return parseURLFromJson(responseRootNode, productURLFieldName);
    }

    public static String parsePhotoURL(JsonNode responseRootNode) throws NullPointerException {
        String photoURLFieldName = "image";
        return parseURLFromJson(responseRootNode, photoURLFieldName);
    }

    private static String parseURLFromJson( JsonNode responseRootNode, String fieldName) {
        return responseRootNode.get("items").get(0).get("pagemap").get("metatags").get(0).get("og:" + fieldName).asText();
    }


    public static String parseInputForBarcode(String jsonInput) throws JsonProcessingException {
        return JsonHandler.jsonStringToNode(jsonInput).get("image_processing_service").get("barcode").asText();
    }


    private static boolean isCorrectUserRate(String userRate) {
        return userRate.contains("★");
    }


}
