package fetcher;

import org.apache.log4j.Logger;
import util.ProductData;

import java.io.IOException;


public class Logic {

    private static final Logger log = Logger.getLogger(Logic.class);


    public static boolean thereAreAvailableCredentials;

    private final DataFetcher dataFetcher;
    private final Engine engine;

    private static int startAttempt = 0;
    private static final int MAX_RESTART_ATTEMPTS = 3;

    private String query;
    private ProductData productData;

    private static boolean searchAttemptFailed = true;


    public Logic(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
        this.engine = dataFetcher.getFetcherEngine();

    }

    public ProductData startSearch(String barcode) {

        this.query = barcode;

        try {

            log.debug("Search attempt " + barcode);
            trySearch();

        } catch (CredentialsDayLimitException e) {
            log.debug("Case#2: search attempts reached day limit for selected credentials.");
            return handleDayLimitException();
        }

        log.debug("Case#1: search attempt successful. Returning Program Data");
        return productData;
    }

    private ProductData handleDayLimitException() {

        log.info("Changing credentials.");

        tryToSearchWithDifferentKey();


        if (!searchAttemptFailed) {
            log.info("Search attempt successful. Returning Program Data.");
            return productData;
        }


        log.debug("Case#3: All keys has expired for today.");
        return new ProductData(getServiceUnavailableDescription());

    }

    private String getServiceUnavailableDescription() {
        return "В данный момент сервис недоступен! Пожалуйста, повторите попытку позднее!";
    }


    private void tryToSearchWithDifferentKey() {


        for (; startAttempt < MAX_RESTART_ATTEMPTS; startAttempt++) {
            log.debug(String.format("Search attempt %s!", startAttempt));

            while (searchAttemptFailed && thereAreAvailableCredentials) {
                log.info("Keeping searching until success or we are out of available keys.");
                dataFetcher.switchEngineCredentials();

                try {
                    trySearch();
                } catch (CredentialsDayLimitException credentialsDayLimitException) {
                    log.info("Search attempts reached day limit for selected key as well.");
                }
            }

        }

        //  Resetting values in order to be able to iterate through
        //  all existing credentials in case of reaching limits.

        Credentials.resetUsedCredentials();
        resetAttempts();
    }


    private void trySearch() throws CredentialsDayLimitException {

        try {
            productData = engine.findProductData(query);
            searchAttemptFailed = false;

        } catch (CredentialsDayLimitException e) {
            searchAttemptFailed = true;
            log.error(e);
            throw new CredentialsDayLimitException();
        } catch (IOException e) {
            searchAttemptFailed = true;
            log.error(e);
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    private static void resetAttempts() {
        log.info("Resetting attempts");
        startAttempt = 0;
    }


}
