package util.jsonhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonHandler {

    private static String receivedJson;
    private static String searchResultJson;

    private static final ObjectMapper objectMapper = getDefaultObjectMapper();


    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultMapper = new ObjectMapper();
        // ----
        return defaultMapper;
    }



    public static JsonNode jsonStringToNode(String jsonSrc) throws JsonProcessingException {
        return objectMapper.readTree(jsonSrc);
    }

    public static JsonNode toJson(Object object) {
        return objectMapper.valueToTree(object);
    }


    public static String jsonNodeToString(JsonNode node, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(node);
    }

    public static <A> A deserializeToClass(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(node, clazz);
    }

    public static Map<String, String> jsonToMap(JsonNode node) throws JsonProcessingException {
        String string = jsonNodeToString(node, false);
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
        return objectMapper.readValue(string, typeRef);
    }

    public static String serializeToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static String putDataInJson(String inputJson, String dataJson) throws JSONException {
        JSONObject inputJsonObject = new JSONObject(inputJson);
        JSONObject dataJsonObject = new JSONObject(dataJson);
        JSONObject outputJsonObject = inputJsonObject.put("fetcher_service", dataJsonObject);

        return outputJsonObject.toString();
    }

//TODO УБРАТЬ ЛИШНЕЕ

    public static void setReceivedJson(String jsonReceivedMessage) {
        receivedJson = jsonReceivedMessage;
    }

    public static String getReceivedJson() {
        return receivedJson;
    }

    public static void setSearchResultJson(String jsonSearchResult) {
        searchResultJson = jsonSearchResult;
    }

    public static String searchResultJson() {
        return searchResultJson;
    }
}
