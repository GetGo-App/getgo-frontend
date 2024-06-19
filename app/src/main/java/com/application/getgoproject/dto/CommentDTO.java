package com.application.getgoproject.dto;

import java.util.List;

public class CommentDTO {
    private String content;
    private List<String> images;
    private float rating;
    private String userId;
    private int location;

    public CommentDTO(String content, List<String> images, float rating, String userId, int location) {
        this.content = content;
        this.images = images;
        this.rating = rating;
        this.userId = userId;
        this.location = location;
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

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}