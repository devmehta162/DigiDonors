package com.dm.digidonors.models;

public class UserProfile {
    private String userName;
    private String userEmail;
    private String userImageurl;
    private String userPhoneNumber;
    private String userAddress;
    private String userUid;

    public UserProfile(String userName, String userEmail, String userImageurl, String userPhoneNumber, String userAddress, String userUid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImageurl = userImageurl;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userUid = userUid;
    }

    public UserProfile() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserImageurl() {
        return userImageurl;
    }

    public void setUserImageurl(String userImageurl) {
        this.userImageurl = userImageurl;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
