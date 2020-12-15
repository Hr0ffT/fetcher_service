package util;

public class MessageData {

    String userName;
    String tgLink;
    String userID;
    String languageCode;

    String messageDate;
    String messageTime;
    String chatID;
    String updateID;

    public MessageData(String userName, String tgLink, String userID, String languageCode, String messageDate, String messageTime, String chatID, String updateID) {
        this.userName = userName;
        this.tgLink = tgLink;
        this.userID = userID;
        this.languageCode = languageCode;
        this.messageDate = messageDate;
        this.messageTime = messageTime;
        this.chatID = chatID;
        this.updateID = updateID;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTgLink() {
        return tgLink;
    }

    public void setTgLink(String tgLink) {
        this.tgLink = tgLink;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getUpdateID() {
        return updateID;
    }

    public void setUpdateID(String updateID) {
        this.updateID = updateID;
    }
}
