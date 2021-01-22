package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import fetcher.CredentialsDayLimitException;
import fetcher.DataFetcher;
import org.apache.log4j.Logger;
import org.json.JSONException;
import rabbit.Rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProcessHandler {

    private static final Logger log = Logger.getLogger(ProcessHandler.class);

    private static final String START_MESSAGE = " ----- fetcher_service started -----";


    private static boolean initialized = false;

    private static Rabbit rabbit;
    private static DataFetcher dataFetcher;

    private static String barcode;
    private static String jsonProductData;
    private static String jsonInput;
    private static String jsonOutput;


    public static void initProgram() throws IOException, TimeoutException {
        System.out.println(START_MESSAGE);
        if (!initialized) {
            dataFetcher = new DataFetcher();
            rabbit = new Rabbit();

            initialized = true;
        }
    }


    public static void messageReceived(String jsonInputMessage) throws JsonProcessingException {
        jsonInput = jsonInputMessage;

        getBarcodeFromInput(jsonInput);

        if (barcodeIsFound()) {
            findProductDataByBarcodeAsJson(barcode);

        } else {
            log.info("Barcode has not been recognized.");
            prepareProductNotFoundJson();
        }

        prepareOutputJson();
        sendOutputJson();

    }

    private static void prepareProductNotFoundJson() throws JsonProcessingException {

        String productNotFoundDescription =
                "Не удалось распознать штрихкод! Пожалуйста, попробуйте снова! " +
                        "Бот принимает фото упаковки товара с четким изображением штрихкода, " +
                        "отправленное в качестве вложения без сжатия.";

        jsonProductData = JsonHandler.serializeToJson(
                new ProductData(productNotFoundDescription));
    }

    private static void prepareOutputJson() {
        try {
            jsonOutput = JsonHandler.putDataInJson(jsonInput, jsonProductData);
        } catch (JSONException e) {
            log.error(e);
        }
    }

    private static void sendOutputJson() {
        rabbit.send(jsonOutput);
    }

    private static void getBarcodeFromInput(String inputJson) {
        try {
            barcode = Parser.parseInputForBarcode(inputJson);
        } catch (JsonProcessingException e) {
            log.error(e);
        }
    }

    private static void findProductDataByBarcodeAsJson(String barcode) {

        try {
            jsonProductData = dataFetcher.fetchProductDataAsJson(barcode);
        } catch (IOException | CredentialsDayLimitException | JSONException e) {
            log.error(e);
        }
    }

    private static boolean barcodeIsFound() {
        // image_processing_service will send "-1" if barcode couldn't have been recognized.
        return Long.parseLong(barcode) > 0;
    }

}
