package output;

import util.ProductData;

public class SuccessMessage extends FinalMessage {

//    String lnBreak = "%0A";

    public SuccessMessage(ProductData productData) {
        super();
        this.text = String.format("""
                Найдено:\s
                %s\s
                %s\s
                %s\s
                """, productData.getPhotoURL(), productData.getDescription(), productData.getUserRate());
    }
}
