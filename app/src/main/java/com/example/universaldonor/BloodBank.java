package com.example.universaldonor;

public class BloodBank {

    private String BankName;
    private BloodStats bloodStats;
    private Donations donations;
    private long mobileNumber;
    private double latitude;
    private double longitude;
    private String city;
    private String state;
    private String address;

    public BloodBank(String bankName, BloodStats bloodStats, Donations donations, long mobileNumber, double latitude, double longitude, String city, String state, String address) {
        BankName = bankName;
        this.bloodStats = bloodStats;
        this.donations = donations;
        this.mobileNumber = mobileNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.state = state;
        this.address = address;
    }

    public BloodBank(String bankName, BloodStats bloodStats, double latitude, double longitude, String city, String state) {
        BankName = bankName;
        this.bloodStats = bloodStats;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.state = state;
    }

    public String getBankName() {
        return BankName;
    }

    public BloodStats getBloodStats() {
        return bloodStats;
    }

    public Donations getDonations() {
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

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }
}
