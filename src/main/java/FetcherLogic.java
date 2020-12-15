
import org.json.JSONException;
import org.jsoup.HttpStatusException;

import java.io.IOException;

public class FetcherLogic {


    public static boolean thereAreAvailableCredentials;

    private final DataFetcher dataFetcher;
    private final FetcherEngine fetcherEngine;

    private static int startAttempt = 0;
    private static final int MAX_RESTART_ATTEMPTS = 1;


    public FetcherLogic(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
        this.fetcherEngine = dataFetcher.getFetcherEngine();

    }

    public ProductData startSearch(String query) throws IOException, NoMoreAvailableCredentialsException, JSONException {
        try {

            return fetcherEngine.findProductData(query);

        } catch (HttpStatusException e) {
            // When credentials have reached day limit

            while (thereAreAvailableCredentials) {
                dataFetcher.switchEngineCredentials();
                startSearch(query);
            }

            for (; startAttempt < MAX_RESTART_ATTEMPTS; startAttempt++) {
                restartSearch(query);
            }

            resetAttempts();

            throw new NoMoreAvailableCredentialsException();

        }

    }


    private void restartSearch(String query) throws IOException, NoMoreAvailableCredentialsException, JSONException {

        System.out.println("restarting");
        EngineCredentials.resetUsedCredentials();
        startSearch(query);
    }

    private static void resetAttempts() {
        startAttempt = 0;
    }


}
