package com.application.getgoproject.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Status {
    private String uploader;
    private String content;
    private LocalDateTime uploadedTime;
    private String privacyMode;
    private ArrayList<String> images;
    private ArrayList<String> reactedUsers;

    public Status(String uploader, String content, LocalDateTime uploadedTime, String privacyMode, ArrayList<String> images, ArrayList<String> reactedUsers) {
        this.uploader = uploader;
        this.content = content;
        this.uploadedTime = uploadedTime;
        this.privacyMode = privacyMode;
        this.images = images;
        this.reactedUsers = reactedUsers;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(LocalDateTime uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public String getPrivacyMode() {
        return privacyMode;
    }

    public void setPrivacyMode(String privacyMode) {
        this.privacyMode = privacyMode;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getReactedUsers() {
        return reactedUsers;
    }

    public void setReactedUsers(ArrayList<String> reactedUsers) {
        this.reactedUsers = reactedUsers;
    }
}
