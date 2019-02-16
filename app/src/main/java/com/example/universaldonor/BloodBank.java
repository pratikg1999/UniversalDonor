package com.example.universaldonor;

public class BloodBank {

    private String BankName;
    private BloodStats bloodStats;
    private Donations donations;
    private long mobileNumber;
    String latitude;
    String longitude;
    String address;

    public BloodBank(String bankName, BloodStats bloodStats, Donations donations, long mobileNumber) {
        BankName = bankName;
        this.bloodStats = bloodStats;
        this.donations = donations;
        this.mobileNumber = mobileNumber;
    }

    public BloodBank(){

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
}
