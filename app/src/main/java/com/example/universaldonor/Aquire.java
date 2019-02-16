package com.example.universaldonor;

import java.text.DateFormat;

public class Aquire {

    private String userId;
    private String bloodBankId;
    private DateFormat date;
    private String bloodType;
    private long bloodAmount;

    public Aquire(String userId, String bloodBankId, DateFormat date, String bloodType, long bloodAmount) {
        this.userId = userId;
        this.bloodBankId = bloodBankId;
        this.date = date;
        this.bloodType = bloodType;
        this.bloodAmount = bloodAmount;
    }

    public Aquire() {
    }

    public String getUserId() {
        return userId;
    }

    public String getBloodBankId() {
        return bloodBankId;
    }

    public DateFormat getDate() {
        return date;
    }

    public String getBloodType() {
        return bloodType;
    }

    public long getBloodAmount() {
        return bloodAmount;
    }
}
