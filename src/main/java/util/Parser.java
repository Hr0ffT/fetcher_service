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
        return responseRootNode.get("items").get(0).get("pagemap").get("metatags").get(0).get("og:url").asText();
    }

    public static String parsePhotoURL(JsonNode responseRootNode) {
        return responseRootNode.get("items").get(0).get("pagemap").get("metatags").get(0).get("og:image").asText();
    }


    public static String parseInputForBarcode(String jsonInput) throws JsonProcessingException {
        return JsonHandler.jsonStringToNode(jsonInput).get("telegram_input_service").get("message").get("text").asText();
    }


    private static boolean isCorrectUserRate(String userRate) {
        return userRate.contains("★");
    }


}
