package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import fetcher.DataFetcher;
import fetcher.NoMoreAvailableCredentialsException;
import org.json.JSONException;
import receiver.Receiver;
import util.jsonhandler.JsonHandler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Handler {

    private static boolean initialized = false;


    private static Receiver receiver;
    private static DataFetcher dataFetcher;
    //    Sender sender;


    public static void initProgram() throws IOException, TimeoutException {
        if (!initialized) {
            dataFetcher = new DataFetcher();
            receiver = new Receiver(dataFetcher);

            initialized = true;
        }
    }


    public static void messageReceived(String jsonInput) {

        try {


            String barcode = getBarcodeFromInput(jsonInput);
            String jsonProductData = findProductDataByBarcodeAsJson(barcode);
            String outputJson = JsonHandler.putDataInJson(jsonInput, jsonProductData);
            System.out.println(outputJson);

            //            JSONArray tg = new JSONArray(jsonInput);
//            System.out.println(tg);
//            JSONObject data = new JSONObject(jsonProductData);
//
//            JSONArray put = tg.put(data);
//            System.out.println(put.toString());


        } catch (IOException | NoMoreAvailableCredentialsException | JSONException e) {
            e.printStackTrace();
        }




        //TODO ОТПРАВИТЬ JSON

//TODO ОТПРАВЛЯТЬ ОШИБКУ, ЕСЛИ НЕ УДАЛОСЬ НАЙТИ ПРОДУКТ!!


    }



    private static String getBarcodeFromInput(String inputJson) throws JsonProcessingException {
               return Parser.parseInputForBarcode(inputJson);
    }

    private static String findProductDataByBarcodeAsJson(String barcode) throws JSONException, NoMoreAvailableCredentialsException, IOException {
        return dataFetcher.fetchProductDataAsJson(barcode);
    }

}
