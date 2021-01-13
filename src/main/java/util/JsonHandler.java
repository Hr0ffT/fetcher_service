package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import rabbit.MQData;

import java.io.IOException;
import java.nio.file.Path;


public class JsonHandler {


    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    public static MQData deserializeMQData(Path mqDataPath) throws IOException {

        return objectMapper.readValue(mqDataPath.toFile(), MQData.class);

    }

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultMapper = new ObjectMapper();
        return defaultMapper;
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
        JSONObject outputJsonObject = inputJsonObject.put("fetcher_service", dataJsonObject);

        return outputJsonObject.toString();
    }


}
