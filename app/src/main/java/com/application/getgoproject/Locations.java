package com.application.getgoproject;

import java.util.List;

public class Locations {
    private String name;
    private String address;
    private String shortDescription;
    private String city;
    private float latitude;
    private float longitude;
//    private List<String> image;
    private int image;
    private boolean isAvailable;
    private String openTime;
    private String detailUrl;
    private String hotline;
    private String price;
    private String categoryId;
    private float rating;
    private boolean isTrend;
    private boolean isTopyear;


//    public Locations(String name, String address, String shortDescription, String city, float latitude, float longitude, List<String> image, boolean isAvailable, String openTime, String detailUrl, String hotline, String price, String categoryId, float rating, boolean isTrend, boolean isTopyear) {
//        this.name = name;
//        this.address = address;
//        this.shortDescription = shortDescription;
//        this.city = city;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.image = image;
//        this.isAvailable = isAvailable;
//        this.openTime = openTime;
//        this.detailUrl = detailUrl;
//        this.hotline = hotline;
//        this.price = price;
//        this.categoryId = categoryId;
//        this.rating = rating;
//        this.isTrend = isTrend;
//        this.isTopyear = isTopyear;
//    }

    public Locations(String name, String address, String shortDescription, String city, float latitude, float longitude, int image, boolean isAvailable, String openTime, String detailUrl, String hotline, String price, String categoryId, float rating, boolean isTrend, boolean isTopyear) {
        this.name = name;
        this.address = address;
        this.shortDescription = shortDescription;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.isAvailable = isAvailable;
        this.openTime = openTime;
        this.detailUrl = detailUrl;
        this.hotline = hotline;
        this.price = price;
        this.categoryId = categoryId;
        this.rating = rating;
        this.isTrend = isTrend;
        this.isTopyear = isTopyear;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

//    public List<String> getImage() {
//        return image;
//    }
//
//    public void setImage(List<String> image) {
//        this.image = image;
//    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isTrend() {
        return isTrend;
    }

    public void setTrend(boolean trend) {
        isTrend = trend;
    }

    public boolean isTopyear() {
        return isTopyear;
    }

    public void setTopyear(boolean topyear) {
        isTopyear = topyear;
    }
}
