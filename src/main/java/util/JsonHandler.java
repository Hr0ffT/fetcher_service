package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;


public class JsonHandler {

    private static final String SERVICE_NAME = ProcessHandler.getServiceName();


    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    public static Map<String, String> jsonFileToMap(URL jsonFileURL) throws IOException {

        return objectMapper.readValue(jsonFileURL, new TypeReference<LinkedHashMap<String, String>>() {
        });
    }

    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static JsonNode jsonStringToNode(String jsonSrc) throws JsonProcessingException {
        return objectMapper.readTree(jsonSrc);
    }

    public static String serializeToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static String putDataInJson(String inputJson, String dataJson) throws JSONException {
        JSONObject inputJsonObject = new JSONObject(inputJson);
        JSONObject dataJsonObject = new JSONObject(dataJson);
        JSONObject outputJsonObject = inputJsonObject.put(SERVICE_NAME, dataJsonObject);

        return outputJsonObject.toString();
    }


}
