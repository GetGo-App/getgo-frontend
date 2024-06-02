package com.application.getgoproject.models;

public class Message {
    private String username;
    private String userMessage;
    private String lastTime;
    private int userAvatar;

    public Message(String username, String userMessage, String lastTime, int userAvatar) {
        this.username = username;
        this.userMessage = userMessage;
        this.lastTime = lastTime;
        this.userAvatar = userAvatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
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
}
