package com.dm.digidonors.models;

public class AddNewAddressModel {
    private String name;
    private String phone;
    private String pincode;
    private String address;
    private String city;
    private String state;


    public AddNewAddressModel() {
    }

    public AddNewAddressModel(String name, String phone, String pincode, String address, String city, String state) {
        this.name = name;
        this.phone = phone;
        this.pincode = pincode;
        this.address = address;
        this.city = city;
        this.state = state;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
