
import org.jsoup.HttpStatusException;

import java.io.IOException;

public class FetcherLogic {


    public static boolean thereAreAvailableCredentials;

    private final DataFetcher dataFetcher;
    private final FetcherEngine fetcherEngine;

    int maxAttempts = 1;


    //    int maxAttempts = SearchEngineParameters.getPoolSize() * 2;


    public FetcherLogic(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
        this.fetcherEngine = dataFetcher.getSearchEngine();

    }


    private void restart() {
        System.out.println("restarting");
        EngineCredentials.resetUsedCredentials();
    }

    //    private void initLogic() {
    //
    //        thereIsAvailableSearchEngine = true;
    //
    //
    //        //        while (true) {
    //        //
    //        //            System.out.println();
    //        //            System.out.println("NEED TO INCREASE POOL!!!!");
    //        //            //            Thread.sleep(10000);
    //        //            initLogic();
    //        //        }
    //
    //    }

    //    private void search(String query) {
    //        try {
    //            searchEngine.findProductData(query);
    //        } catch (IOException e) {
    //            while (thereIsAvailableSearchEngine) {
    //                searcher.switchEngineCredentials();
    //                search(query);
    //            }
    //            System.out.println("This engine is not available");
    //            System.out.println();
    //
    //        }
    //    }

    public ProductData startSearch(String query) throws IOException {
        try {

            return fetcherEngine.findProductData(query);

        } catch (HttpStatusException e) {
            System.out.println("This engine is not available");

            while (thereAreAvailableCredentials) {
                dataFetcher.switchEngineCredentials();
                startSearch(query);
            }


        }


    }
    //    public void attemptSearch(String barcode) {
    //
    //        int attempt = 0;
    //
    //        while (attempt < maxAttempts) {
    //
    ////            search(barcode);
    //
    //            attempt++;
    //            restart();
    //        }
    //    }
}
