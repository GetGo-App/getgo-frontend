package com.application.getgoproject.models;

import java.util.ArrayList;

public class Status {
    private String uploader;
    private String title;
    private String content;
    private String uploadedTime;
    private String privacyMode;
    private int userAvatar;
    private ArrayList<Integer> images;
    private ArrayList<String> reactedUsers;

    public Status(String uploader, String title, String content, String uploadedTime, String privacyMode, int userAvatar, ArrayList<Integer> images, ArrayList<String> reactedUsers) {
        this.uploader = uploader;
        this.title = title;
        this.content = content;
        this.uploadedTime = uploadedTime;
        this.privacyMode = privacyMode;
        this.userAvatar = userAvatar;
        this.images = images;
        this.reactedUsers = reactedUsers;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public String getPrivacyMode() {
        return privacyMode;
    }

    public void setPrivacyMode(String privacyMode) {
        this.privacyMode = privacyMode;
    }

    public int getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(int userAvatar) {
        this.userAvatar = userAvatar;
    }

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void setImages(ArrayList<Integer> images) {
        this.images = images;
    }

    public ArrayList<String> getReactedUsers() {
        return reactedUsers;
    }

    public void setReactedUsers(ArrayList<String> reactedUsers) {
        this.reactedUsers = reactedUsers;
    }
}
