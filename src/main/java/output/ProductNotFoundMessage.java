package output;

public class ProductNotFoundMessage extends FinalMessage {

    public ProductNotFoundMessage(String barcode) {
        super();
        this.text = String.format(
                "К сожалению, продукт со штрихкодом %s не был найден! " +
                        "Пожалуйста, проверьте совпадают ли указанные цифры с кодом, " +
                        "указанным на упаковке и попробуйте еще раз. " +
                        "Товары, не сертифицированные на территории РФ, могут быть не найдены. ", barcode);
    }
}

