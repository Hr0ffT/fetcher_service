package output;

public class NoBarcodeMessage extends FinalMessage {

    public NoBarcodeMessage() {
        super();
        this.text = "Не удалось распознать штрихкод! Пожалуйста, попробуйте снова! " +
            "Бот принимает фото упаковки товара с четким изображением штрихкода, " +
            "отправленное в качестве вложения без сжатия.";
    }
}
