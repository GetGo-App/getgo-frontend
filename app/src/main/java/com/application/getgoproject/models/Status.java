package com.application.getgoproject.models;

import java.util.ArrayList;

public class Status {
    private String username;
    private String title;
    private String content;
    private String lastTime;
    private int userAvatar;
    private ArrayList<Integer> userImage;

    public Status(String username, String title, String content, String lastTime, int userAvatar, ArrayList<Integer> userImage) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.lastTime = lastTime;
        this.userAvatar = userAvatar;
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(int userAvatar) {
        this.userAvatar = userAvatar;
    }

    public ArrayList<Integer> getUserImage() {
        return userImage;
    }

    public void setUserImage(ArrayList<Integer> userImage) {
        this.userImage = userImage;
    }
}
