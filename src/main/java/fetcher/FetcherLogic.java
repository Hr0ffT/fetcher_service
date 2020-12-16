package fetcher;

import org.json.JSONException;
import org.jsoup.HttpStatusException;
import util.ProductData;

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

    public ProductData startSearch(String query) throws NoMoreAvailableCredentialsException {

        try {

            return fetcherEngine.findProductData(query);

        } catch (IOException | NoMoreAvailableCredentialsException e) {

            // When credentials have reached day limit

            while (thereAreAvailableCredentials) {

                dataFetcher.switchEngineCredentials();
                startSearch(query);

            }

            if (startAttempt < MAX_RESTART_ATTEMPTS) {
                try {

                    startAttempt++;
                    restartSearch(query);

                } catch (IOException | JSONException ioException) {
                    ioException.printStackTrace();
                }
            }

            resetAttempts();



            // TODO SENDER ДОЛЖЕН ОТПРАВИТЬ СООБЩЕНИЕ О НЕУДАЧНОЙ ПОПЫТКЕ (НЕТ ДОСТУПНЫХ ПОИСКОВИКОВ)



            throw new NoMoreAvailableCredentialsException();
        }

    }


    private void restartSearch(String query) throws IOException, NoMoreAvailableCredentialsException, JSONException {

        System.out.println("restarting search");
        EngineCredentials.resetUsedCredentials();
        startSearch(query);
    }

    private static void resetAttempts() {
        startAttempt = 0;
    }


}
