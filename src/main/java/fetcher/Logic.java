package fetcher;


import util.ProductData;

import java.io.IOException;


public class Logic {


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
            System.out.println("Попытка поиска");
            trySearch();

        } catch (CredentialsDayLimitException e) {
            System.out.println("сценарий #2 - у активированного ключа закончились попытки поиска");

            //Ниче страшного, есть для этого механизм!

            handleDayLimitException(); //  ------------------------------->   Вот тут - - -> |
        }               //                                                                   |
        //                                                                                           |
        //                                                                                           |
        System.out.println("сценарий #1 -  все хорошо, возвращаем данные");  //              |
        return productData;                                                  //              |
        //               |
    }    //                                                                                  |

    //                                                                                           |
    private ProductData handleDayLimitException() {  // <--------- Вот же он!! <- - - - - -  |


        //сценарий #2 -  закончились попытки поиска у ключа


        // ЧТО ДЕЛАТЬ?!??!


        //спокуха!
        System.out.println("Пробуем поменять ключ на другой");


        // пробуем поискать с разными ключами!
        tryToSearchWithDifferentKey();

        //проверим результат:

        if (!searchAttemptFailed) {

            System.out.println("Мы все нашли, передаем данные!");

            return productData;
        }


        // Если до сюда дошли, значит - пизда:


        // Сценарий #3 -  самый плохой!


        System.out.println("Сценарий #3 -  самый плохой!");
        System.out.println("Все ключи израсходованы!");


        //
        //
        //
        //                ХУЛЕ ДЕЛАТЬ?!?!?!??!?!!?!??!
        //
        //// TODO SENDER ДОЛЖЕН ОТПРАВИТЬ СООБЩЕНИЕ О НЕУДАЧНОЙ ПОПЫТКЕ (НЕТ ДОСТУПНЫХ ПОИСКОВИКОВ)
        //
        //


        System.out.println("У всех ключей кончились попытки, мы нихера не нашли!!");


        // ТВАЮ МАТЬ!!

        return null;
        //хуй вам че вернем, в этом случае, зовите админа, пусть ищет новые креденшелы, а я ВСЕ!

    }


    private void tryToSearchWithDifferentKey() {

        //            На всякий случай сделаем это несколько раз
        for (; startAttempt < MAX_RESTART_ATTEMPTS; startAttempt++) {
            System.out.println("Попытка номер: " + startAttempt + "!");

            while (searchAttemptFailed && thereAreAvailableCredentials) {
                System.out.println("Пробуем искать, пока не получится найти или не закончатся доступные ключи");
                dataFetcher.switchEngineCredentials();
                try {
                    trySearch();
                } catch (CredentialsDayLimitException credentialsDayLimitException) {
                    System.out.println("Снова-здорова, с этим ключом такая же беда!");
                    System.out.println("Что ж, попробуем еще, пока есть ключи...");
                }
            }

        }


        //После всех попыток, вернем переменные на стартовое значение,
        // чтобы пройти этот страшный путь заново, есть снова возникнет такая проблема

        Credentials.resetUsedCredentials();
        resetAttempts();
    }


    private void trySearch() throws CredentialsDayLimitException {
        try {
            System.out.println("Ищем " + query);
            productData = engine.findProductData(query);
            searchAttemptFailed = false;

        } catch (CredentialsDayLimitException e) {
            System.out.println("Проавл попытки поиска, у данного ключа закончились попытки!");
            searchAttemptFailed = true;
            e.printStackTrace();
            throw new CredentialsDayLimitException();

        } catch (IOException e) {
            System.out.println("Проавл попытки поиска! IOException");
            searchAttemptFailed = true;
            e.printStackTrace();
        }
    }

    private static void resetAttempts() {
        startAttempt = 0;
    }


}
