package util;

import fetcher.DataFetcher;
import fetcher.NoMoreAvailableCredentialsException;
import org.json.JSONException;
import receiver.Receiver;

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


    public static void messageReceived(String messageJson) {
//        System.out.println( messageJson);

        try {

            //parseMessageDataFromJson

            // PUT MESSAGE DATA AND PRODUCT DATA IN OUTPIUT DATA AND DESERIALIZE IN TO JSON THEN SEND

            String barcode = getBarcodeFromMessage(messageJson);

//            ProductData productData = findProductDataByBarcode(barcode);
            String productData = findProductDataByBarcodeAsJson(barcode);
            System.out.println(productData);


//            System.out.println(jsonProductData);




        } catch (JSONException | NoMoreAvailableCredentialsException | IOException e) {
            e.printStackTrace();
        }

    }

    private static String getBarcodeFromMessage(String messageJson) throws JSONException {
        return Parser.parseBarcodeFromMessage(messageJson);
    }

    private static ProductData findProductDataByBarcode(String barcode) throws JSONException, NoMoreAvailableCredentialsException, IOException {
        return dataFetcher.fetchProductData(barcode);
    }

    private static String findProductDataByBarcodeAsJson(String barcode) throws JSONException, NoMoreAvailableCredentialsException, IOException {
        return dataFetcher.fetchProductDataAsJson(barcode);
    }

}
