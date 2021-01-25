package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.ProductNotFoundException;
import exceptions.ServiceUnavailableException;
import fetcher.DataFetcher;
import org.apache.log4j.Logger;
import org.json.JSONException;
import output.*;
import rabbit.Rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProcessHandler {

    //todo добавить тесты

    private static final Logger log = Logger.getLogger(ProcessHandler.class);

    public static final String SERVICE_NAME = "fetcher_service";

    private static final String START_MESSAGE = String.format(" ----- %s started -----", SERVICE_NAME);


    private static boolean initialized = false;

    private static Rabbit rabbit;
    private static DataFetcher dataFetcher;

    private static String barcode;
    private static String jsonFinalMessage;
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


            try {


                ProductData productData = findProductDataByBarcode(barcode);
                prepareJsonFinalMessage(new SuccessMessage(productData));


            } catch (ProductNotFoundException e) {

                prepareJsonFinalMessage(new ProductNotFoundMessage(barcode));
                log.error(e);

            } catch (ServiceUnavailableException e) {

                prepareJsonFinalMessage(new ServiceUnavailableMessage());
                log.error(e);

            }


        } else {

            log.info("Barcode has not been recognized.");
            prepareJsonFinalMessage(new NoBarcodeMessage());

        }

        prepareOutputJson();
        sendOutputJson();

    }

    private static void prepareJsonFinalMessage(FinalMessage finalMessage) throws JsonProcessingException {
        jsonFinalMessage = JsonHandler.serializeToJson(finalMessage);
    }

    private static void prepareOutputJson() {
        try {

            jsonOutput = JsonHandler.putDataInJson(jsonInput, jsonFinalMessage);

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

    private static ProductData findProductDataByBarcode(String barcode) throws ProductNotFoundException, ServiceUnavailableException {

        return dataFetcher.fetchProductData(barcode);

    }

    private static boolean barcodeIsFound() {
        // image_processing_service will send "-1" if barcode couldn't have been recognized.
        return Long.parseLong(barcode) > 0;
    }


}
