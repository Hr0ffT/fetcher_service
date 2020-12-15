import java.io.IOException;

public class Runner {



    public static void main(String[] args) throws IOException {

        final DataFetcher dataFetcher = new DataFetcher();


        try {
            dataFetcher.fetch("4607099093508");
        } catch (NoMoreAvailableCredentialsException e) {
            System.out.println("All credentials have reached day limit");
            e.printStackTrace();
        }


    }



}
