package com.example.universaldonor;

import java.util.ArrayList;

public class BloodBank {

    private String bankName;
    private BloodStats bloodStats;
    private ArrayList<String> donations;
    private long mobileNumber;
    private double latitude;
    private double longitude;
    private String address;
    private String city;
    private  String state;
    private String email;

    public BloodBank(String bankName, BloodStats bloodStats, ArrayList<String> donations, long mobileNumber, double latitude, double longitude, String address, String city, String state, String email) {
        this.bankName = bankName;
        this.bloodStats = bloodStats;
        this.donations = donations;
        this.mobileNumber = mobileNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
    }

    public BloodBank() {
    }

    public String getBankName() {
        return bankName;
    }

    public BloodStats getBloodStats() {
        return bloodStats;
    }

    public ArrayList<String> getDonations() {
        return donations;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }
}


