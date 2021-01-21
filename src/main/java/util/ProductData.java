package util;

public class ProductData {

    boolean notFound = false;
    String photoURL;
    String description;
    String userRate;


    public ProductData(String photoURL, String userRate, String description) {

        this.photoURL = photoURL;
        this.userRate = userRate;
        this.description = description;
    }

    public ProductData(String description) {

        this.notFound = true;
        this.photoURL = "Фото отсутствует";
        this.userRate = "Нет отзывов";
        this.description = description;

    }

    public boolean isNotFound() {
        return notFound;
    }

    public void setNotFound(boolean notFound) {
        this.notFound = notFound;
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
