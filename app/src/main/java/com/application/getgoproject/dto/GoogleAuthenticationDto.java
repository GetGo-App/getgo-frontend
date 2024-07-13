package com.application.getgoproject.dto;

public class GoogleAuthenticationDto {
    private String email;

    public GoogleAuthenticationDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
