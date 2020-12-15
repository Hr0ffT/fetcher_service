import org.json.JSONException;

import java.io.IOException;

public class Runner {



    public static void main(String[] args) throws IOException {

        final DataFetcher dataFetcher = new DataFetcher();


        try {

            System.out.println(

                    dataFetcher.fetch("4607099093508")

            );

        } catch (NoMoreAvailableCredentialsException | JSONException e) {
            System.out.println("All credentials have reached day limit");
            e.printStackTrace();
        }


    }



}
