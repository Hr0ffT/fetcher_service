package util;

import fetcher.DataFetcher;
import sender.Sender;
import fetcher.CredentialsDayLimitException;

import com.fasterxml.jackson.core.JsonProcessingException;


import org.json.JSONException;
import receiver.Receiver;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Handler {

    private static boolean initialized = false;

    private static DataFetcher dataFetcher;
    private static Sender sender;

    private static String barcode;
    private static String jsonProductData;
    private static String jsonInput;
    private static String jsonOutput;


    public static void initProgram() throws IOException, TimeoutException {
        if (!initialized) {
            dataFetcher = new DataFetcher();
            Receiver receiver = initReceiver();
            sender = new Sender(receiver);

            initialized = true;
        }
    }

    private static Receiver initReceiver() throws IOException, TimeoutException {
        return Receiver.initReceiver();
    }


    public static void messageReceived(String jsonInputMessage) throws JsonProcessingException {
        jsonInput = jsonInputMessage;

        getBarcodeFromInput(jsonInput);

        if (isCorrectBarcode()) {
            System.out.println(" BARCODE IS OK " + barcode);
            findProductDataByBarcodeAsJson(barcode);
        } else {

            System.out.println("Запрос - говно!");
            jsonProductData = JsonHandler.serializeToJson(new ProductData(" ", " ", "ВАШ ЗАПРОС ГОВНО!"));


        }
        System.out.println("Пошел на отправку!");
        prepareOutputJson();
        sendOutputJson();


        //ОТПРАВЛЯЕМ!!!!


        //TODO ОТПРАВЛЯТЬ ОШИБКУ, ЕСЛИ НЕ УДАЛОСЬ НАЙТИ ПРОДУКТ!!


    }

    private static void prepareOutputJson() {
        try {
            jsonOutput = JsonHandler.putDataInJson(jsonInput, jsonProductData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void sendOutputJson() {
        sender.send(jsonOutput);
    }


    private static void getBarcodeFromInput(String inputJson) {
        try {
            barcode = Parser.parseInputForBarcode(inputJson);
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

    private static boolean isCorrectBarcode() {


        try {
            Integer.parseInt(barcode);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }

}
