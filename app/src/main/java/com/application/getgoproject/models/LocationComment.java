package com.application.getgoproject.models;

import java.time.LocalDateTime;
import java.util.List;

public class LocationComment {
    private String id;
    private String content;
    private List<String> images;
    private float rating;
    private String userId;
    private LocalDateTime createdDate;
    private int location;

    public LocationComment(String id, String content, List<String> images, float rating, String userId, LocalDateTime createdDate, int location) {
        this.id = id;
        this.content = content;
        this.images = images;
        this.rating = rating;
        this.userId = userId;
        this.createdDate = createdDate;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
