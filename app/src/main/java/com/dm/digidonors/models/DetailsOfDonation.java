package com.dm.digidonors.models;

public class DetailsOfDonation {

    private String name;
    private String phone;
    private String address;
    private String pincode;
    private String city;
    private String state;
    private String no;
    private String dateforpickup;
    private String timeforpickup;
    private String category;
    private String description;
    private String time;
    private String donationid;
    private String userid;
    private String status;
    private String otp;
    private String userdonateid;
    private String serviceType;
    private String fcmtoken;


    public DetailsOfDonation() {
    }

    public DetailsOfDonation(String name, String phone, String address, String pincode, String city, String state, String no, String dateforpickup, String timeforpickup, String category, String description, String time, String donationid, String userid, String status, String otp, String userdonateid, String serviceType, String fcmtoken) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.no = no;
        this.dateforpickup = dateforpickup;
        this.timeforpickup = timeforpickup;
        this.category = category;
        this.description = description;
        this.time = time;
        this.donationid = donationid;
        this.userid = userid;
        this.status = status;
        this.otp = otp;
        this.userdonateid = userdonateid;
        this.serviceType = serviceType;
        this.fcmtoken = fcmtoken;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDateforpickup() {
        return dateforpickup;
    }

    public void setDateforpickup(String dateforpickup) {
        this.dateforpickup = dateforpickup;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeforpickup() {
        return timeforpickup;
    }

    public void setTimeforpickup(String timeforpickup) {
        this.timeforpickup = timeforpickup;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDonationid() {
        return donationid;
    }

    public void setDonationid(String donationid) {
        this.donationid = donationid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUserdonateid() {
        return userdonateid;
    }

    public void setUserdonateid(String userdonateid) {
        this.userdonateid = userdonateid;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }
}
