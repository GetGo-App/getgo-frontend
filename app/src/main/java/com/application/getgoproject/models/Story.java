package com.application.getgoproject.models;

public class Story {
    private String username;
    private int avatar;

    public Story(String username, int avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
