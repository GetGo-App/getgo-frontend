package com.application.getgoproject.models;

public class UserAuthentication {
    private String accessToken;
    private String username;
    private String email;
    private boolean isActive;

    public UserAuthentication() {

    }

    public UserAuthentication(String accessToken, String username, String email, boolean isActive) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.isActive = isActive;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
