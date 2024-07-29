package com.application.getgoproject.dto;

public class ReactStoryDTO {
    private String reactedUserId;

    public ReactStoryDTO(String reactedUserId) {
        this.reactedUserId = reactedUserId;
    }

    public String getReactedUserId() {
        return reactedUserId;
    }

    public void setReactedUserId(String reactedUserId) {
        this.reactedUserId = reactedUserId;
    }
}
