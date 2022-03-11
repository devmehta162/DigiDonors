package com.dm.digidonors.MyDonation;

public class MyDonationModel {

    private String name;
    private String phone;
    private String address;
    private String no;
    private String city;
    private String state;
    private String participate;
    private String dateforpickup;
    private String timeforpickup;
    private String description;
    private String category;
    private String status;
    private String time;
    private String otp;

    public MyDonationModel() {
    }

    public MyDonationModel(String name, String phone, String address, String no, String city, String state, String participate, String dateforpickup, String timeforpickup, String description, String category,String status,String time,String otp) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.no = no;
        this.city = city;
        this.state = state;
        this.participate = participate;
        this.dateforpickup = dateforpickup;
        this.timeforpickup = timeforpickup;
        this.description = description;
        this.category = category;
        this.status=status;
        this.time=time;
        this.otp=otp;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public String getParticipate() {
        return participate;
    }

    public void setParticipate(String participate) {
        this.participate = participate;
    }

    public String getDateforpickup() {
        return dateforpickup;
    }

    public void setDateforpickup(String dateforpickup) {
        this.dateforpickup = dateforpickup;
    }

    public String getTimeforpickup() {
        return timeforpickup;
    }

    public void setTimeforpickup(String timeforpickup) {
        this.timeforpickup = timeforpickup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

