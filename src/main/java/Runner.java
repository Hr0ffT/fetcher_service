import org.json.JSONException;

import java.io.IOException;

public class Runner {



    public static void main(String[] args) throws IOException {



        //принять запрос



        final DataFetcher dataFetcher = new DataFetcher();

        try {

            String jsonProductData = dataFetcher.fetch("4607099093508");

            System.out.println(

                    jsonProductData

            );

        } catch (NoMoreAvailableCredentialsException | JSONException e) {
            System.out.println("All credentials have reached day limit");
            e.printStackTrace();
        }




        //отправить json




    }



}
