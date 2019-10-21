package com.example.fisrtapplication.entities;

import com.google.gson.annotations.SerializedName;

public class Vending {
    @SerializedName("name")
    private final String name;
    @SerializedName("company")
    private final String company;
    @SerializedName("good")
    private final String good;
    @SerializedName("location")
    private final String location;
    @SerializedName("url")
    private final String url;

    public Vending(String name, String company, String good,
                   String location, String url) {
        this.name = name;
        this.company = company;
        this.good = good;
        this.location = location;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getGood() {
        return good;
    }

    public String getLocation() {
        return location;
    }

    public String getUrl() {
        return url;
    }
}
