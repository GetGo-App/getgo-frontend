package com.application.getgoproject.models;

import java.time.LocalDateTime;

public class Story {
    private String id;
    private String creator;
    private String linkImage;
    private String caption;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public Story(String id, String creator, String linkImage, String caption, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.id = id;
        this.creator = creator;
        this.linkImage = linkImage;
        this.caption = caption;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }
}
