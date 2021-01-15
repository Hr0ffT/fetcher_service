package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import fetcher.CredentialsDayLimitException;
import fetcher.DataFetcher;
import org.json.JSONException;
import rabbit.Rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Handler {

    private static Rabbit rabbit;

    private static boolean initialized = false;

    private static DataFetcher dataFetcher;


    private static String barcode;
    private static String jsonProductData;
    private static String jsonInput;
    private static String jsonOutput;


    public static void initProgram() throws IOException, TimeoutException {
        if (!initialized) {
            dataFetcher = new DataFetcher();
            rabbit = new Rabbit();


            initialized = true;
        }
    }


    public static void messageReceived(String jsonInputMessage) throws JsonProcessingException {
        jsonInput = jsonInputMessage;


        if (barcodeIsFound()) {
            getBarcodeFromInput(jsonInput);
            findProductDataByBarcodeAsJson(barcode);
        } else {

            System.out.println("Barcode has not been recognized!");
            jsonProductData = JsonHandler.serializeToJson(new ProductData("Не удалось распознать штрихкод! Пожалуйста, попробуйте снова!"));

        }
        System.out.println("Пошел на отправку!");
        prepareOutputJson();
        sendOutputJson();

    }

    private static void prepareOutputJson() {
        try {
            jsonOutput = JsonHandler.putDataInJson(jsonInput, jsonProductData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void sendOutputJson() {
        rabbit.send(jsonOutput);
    }


    private static void getBarcodeFromInput(String inputJson) {
        try {
            barcode = Parser.parseInputForBarcode(inputJson);
            System.out.println(barcode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static void findProductDataByBarcodeAsJson(String barcode) {

        try {
            jsonProductData = dataFetcher.fetchProductDataAsJson(barcode);
        } catch (IOException | CredentialsDayLimitException | JSONException e) {
            e.printStackTrace();
        }
    }

    private static boolean barcodeIsFound() {
        // image_processing_service will send "-1" if barcode couldn't have been recognized.
        return Long.parseLong(barcode) > 0;
    }

}
