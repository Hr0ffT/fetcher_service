public class ProductData {

    String productName;
    String photoURL;
    String userRate;
    String description;


    public ProductData(String productName, String photoURL, String userRate, String description) {
        this.productName = productName;
        this.photoURL = photoURL;
        this.userRate = userRate;
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

}
