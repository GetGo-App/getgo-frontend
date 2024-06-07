package com.application.getgoproject.models;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String gender;
    private String email;
    private String phonenumber;
    private String avatar;
    private String role;
    private boolean isActive;
    private List<String> friends;
    private List<String> favorites;

    public User(String username, String password, String gender, String email, String phonenumber, String avatar, String role, boolean isActive, List<String> friends, List<String> favorites) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.phonenumber = phonenumber;
        this.avatar = avatar;
        this.role = role;
        this.isActive = isActive;
        this.friends = friends;
        this.favorites = favorites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }
}
