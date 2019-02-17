package com.example.universaldonor;

import java.util.ArrayList;

public class User {

    private String userName;
    private String email;
    private String userId;
    private String bloodGroup;
    private long points;
    private long numDonations;
    private long level;
    private String address;
    private double latitude;
    private double longitude;
    private long mobileNumber;
    private ArrayList<String> donations;
    private ArrayList<String> aquires;

    public User(){

    }

    public User(String userName, String email,String userId, String bloodGroup, long points, long numDonations, long level, String address, double latitude, double longitude, long mobileNumber, ArrayList<String> donations, ArrayList<String> aquires) {
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.bloodGroup = bloodGroup;
        this.points = points;
        this.numDonations = numDonations;
        this.level = level;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mobileNumber = mobileNumber;
        this.donations = donations;
        this.aquires = aquires;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public long getPoints() {
        return points;
    }

    public long getNumDonations() {
        return numDonations;
    }

    public long getLevel() {
        return level;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public ArrayList<String> getDonations() {
        return donations;
    }

    public ArrayList<String> getAquires() {
        return aquires;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void setNumDonations(long numDonations) {
        this.numDonations = numDonations;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setDonations(ArrayList<String> donations) {
        this.donations = donations;
    }

    public void setAquires(ArrayList<String> aquires) {
        this.aquires = aquires;
    }
}

