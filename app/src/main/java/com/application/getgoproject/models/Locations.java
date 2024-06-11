package com.application.getgoproject.models;

import java.io.Serializable;
import java.util.List;

public class Locations implements Serializable {
    private int id;
    private String name;
    private String address;
    private String content;
    private String city;
    private float latitude;
    private float longitude;
    private List<String> images;
    //    private int image;
    private boolean isAvailable;
    private String openTime;
    private String detailUrl;
    private String hotline;
    private String price;
    private String categoryId;
    private RatingStar rating;
    private RatingStar websiteRating;
    private float websiteRatingOverall;
    private boolean isTrend;
    private boolean isTopYear;

    public static class RatingStar {
        private float star1;
        private float star2;
        private float star3;
        private float star4;
        private float star5;

        public RatingStar(float star1, float star2, float star3, float star4, float star5) {
            this.star1 = star1;
            this.star2 = star2;
            this.star3 = star3;
            this.star4 = star4;
            this.star5 = star5;
        }

        public float getStar1() {
            return star1;
        }

        public void setStar1(float star1) {
            this.star1 = star1;
        }

        public float getStar2() {
            return star2;
        }

        public void setStar2(float star2) {
            this.star2 = star2;
        }

        public float getStar3() {
            return star3;
        }

        public void setStar3(float star3) {
            this.star3 = star3;
        }

        public float getStar4() {
            return star4;
        }

        public void setStar4(float star4) {
            this.star4 = star4;
        }

        public float getStar5() {
            return star5;
        }

        public void setStar5(float star5) {
            this.star5 = star5;
        }
    }


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


    public Locations(int id, String name, String address, String content, String city, float latitude, float longitude, List<String> images, boolean isAvailable, String openTime, String detailUrl, String hotline, String price, String categoryId, RatingStar rating, RatingStar websiteRating, float websiteRatingOverall, boolean isTrend, boolean isTopYear) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.content = content;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.isAvailable = isAvailable;
        this.openTime = openTime;
        this.detailUrl = detailUrl;
        this.hotline = hotline;
        this.price = price;
        this.categoryId = categoryId;
        this.rating = rating;
        this.websiteRating = websiteRating;
        this.websiteRatingOverall = websiteRatingOverall;
        this.isTrend = isTrend;
        this.isTopYear = isTopYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public RatingStar getRating() {
        return rating;
    }

    public void setRating(RatingStar rating) {
        this.rating = rating;
    }

    public RatingStar getWebsiteRating() {
        return websiteRating;
    }

    public void setWebsiteRating(RatingStar websiteRating) {
        this.websiteRating = websiteRating;
    }

    public float getWebsiteRatingOverall() {
        return websiteRatingOverall;
    }

    public void setWebsiteRatingOverall(float websiteRatingOverall) {
        this.websiteRatingOverall = websiteRatingOverall;
    }

    public boolean isTrend() {
        return isTrend;
    }

    public void setTrend(boolean trend) {
        isTrend = trend;
    }

    public boolean isTopYear() {
        return isTopYear;
    }

    public void setTopYear(boolean topYear) {
        isTopYear = topYear;
    }
}
