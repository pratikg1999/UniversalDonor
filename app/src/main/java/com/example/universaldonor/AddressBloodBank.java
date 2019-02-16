package com.example.universaldonor;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressBloodBank {

    String city;
    String state;
    String country;
    double lat;
    double lng;


    public AddressBloodBank(String city, String state, String country, double lat, double lng) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
    }
}
