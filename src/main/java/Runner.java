import fetcher.DataFetcher;
import fetcher.NoMoreAvailableCredentialsException;
import org.json.JSONException;
import util.Handler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Runner {


    public static void main(String[] args) throws IOException, TimeoutException {

       Handler.initProgram();







        //отправить json


    }

//    private void fetch(DataFetcher dataFetcher) {
//        try {
//
//            String jsonProductData = dataFetcher.fetch("4607099093508");
//
//            System.out.println(
//
//                    jsonProductData
//
//            );
//
//        } catch (NoMoreAvailableCredentialsException | JSONException | IOException e) {
//            System.out.println("All credentials have reached day limit");
//            e.printStackTrace();
//        }
//
//    }


}
