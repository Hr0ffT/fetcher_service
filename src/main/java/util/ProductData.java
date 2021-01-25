package util;

public class ProductData {

    private String photoURL;
    private String description;
    private String userRate;


    public ProductData(String photoURL, String userRate, String description) {
        this.photoURL = photoURL;
        this.userRate = userRate;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
