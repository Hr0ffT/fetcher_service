package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;


public class Parser {

    private static final String ANTECEDENT_SERVICE_NAME = "image_processing_service";


    public static String getTargetValueFromResponse(JsonNode responseRootNode, Fields fieldName) {

        String targetValue = getTargetValueFromJsonNode(responseRootNode, fieldName);

        if (fieldName == Fields.USER_RATE) {
            if (!isCorrectUserRate(targetValue)) {
                return "Оценки пользователей отсутствуют.";
            }
        }
        return targetValue;
    }

    public static String parseInputForBarcode(String jsonInput) throws JsonProcessingException {
        JsonNode antecedentServiceDataNode = JsonHandler.jsonStringToNode(jsonInput).get(ANTECEDENT_SERVICE_NAME);
        String targetField = "barcode";

        return antecedentServiceDataNode.get(targetField).asText();
    }

    private static boolean isCorrectUserRate(String userRate) {
        return userRate.contains("★");
    }

    private static String getTargetValueFromJsonNode(JsonNode responseRootNode, Fields fieldName) {
        JsonNode targetArray = responseRootNode.get("items").get(0).get("pagemap").get("metatags").get(0);

        return targetArray.get("og:" + fieldName.toString()).asText();
    }

    public enum Fields {

        USER_RATE("description"),
        PRODUCT_URL("url"),
        PHOTO_URL("image");

        private final String value;

        Fields(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}


