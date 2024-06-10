package com.application.getgoproject.dto;

import com.application.getgoproject.utils.LocalDateTimeSerializer;
import com.google.gson.annotations.JsonAdapter;

import java.time.LocalDateTime;

public class UpdateUserDTO {

    private String userName;
    private String password;
    private String phoneNumber;
    private String gender;
    private String email;
    private String avatar;

    @JsonAdapter(LocalDateTimeSerializer.class)
    private LocalDateTime birthday;

    public UpdateUserDTO(String userName, String password, String phoneNumber, String gender, String email, String avatar, LocalDateTime birthday) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.avatar = avatar;
        this.birthday = birthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }
}
