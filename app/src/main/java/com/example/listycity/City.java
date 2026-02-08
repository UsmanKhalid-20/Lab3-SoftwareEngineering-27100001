package com.example.listycity;

import java.io.Serializable;

public class City implements Serializable {
    private String city;
    private String province;

    public City(String city, String province) {
        this.city = city;
        this.province = province;
    }

    public String getCityName() {
        return city;
    }

    public void setCityName(String city) {
        this.city = city;
    }

    public String getProvinceName() {
        return province;
    }

    public void setProvinceName(String province) {
        this.province = province;
    }
}