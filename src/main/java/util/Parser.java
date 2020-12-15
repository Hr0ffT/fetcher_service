package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parser {

    public static ProductData parseProductDataFromJson(String jsonSearchResult) throws JSONException {

//        System.out.println(jsonSearchResult); //печать боди

        String productName;
        String photoURL;
        String userRate;
        String description;

        JSONObject jsonObjectSearchResult = new JSONObject(jsonSearchResult);
        JSONArray jsonArrayItems = jsonObjectSearchResult.getJSONArray("items");

        description = jsonArrayItems.getJSONObject(0).getString("snippet").replaceAll("\\n", "");
        photoURL = jsonArrayItems.getJSONObject(0).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:image").replaceAll("\\n", "");
        userRate = jsonArrayItems.getJSONObject(0).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:description").replaceAll("\\n", "");
        productName = jsonArrayItems.getJSONObject(0).getString("title").replaceAll("\\.", "");

        if (!isCorrectUserRate(userRate)) {
            userRate = " ";
        }

        return new ProductData(productName, photoURL, userRate, description);

    }


    public static String parseBarcodeFromMessage(String jsonMessage) throws JSONException {

        JSONObject jsonObjectMessage = new JSONObject(jsonMessage);
        return   jsonObjectMessage.getJSONObject("message").getString("text");

    }

//    public static MessageData parseMessageDataFromJson(String receivedMessage) {
//
//        String userName;
//        String tgLink;
//        String userID;
//        String languageCode;
//
//        String messageDate;
//        String messageTime;
//        String chatID;
//        String updateID;
//
//
//
//
////        v4.json
//
//
//
//    }






    private static boolean isCorrectUserRate(String userRate) {
        return userRate.contains("★");
    }




}
