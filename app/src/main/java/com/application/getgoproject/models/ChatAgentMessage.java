package com.application.getgoproject.models;

import java.util.List;

public class ChatAgentMessage {
    private List<Integer> ids_location;

    private String message;

    public ChatAgentMessage(List<Integer> ids_location, String message) {
        this.ids_location = ids_location;
        this.message = message;
    }

    public List<Integer> getIds_location() {
        return ids_location;
    }

    public void setIds_location(List<Integer> ids_location) {
        this.ids_location = ids_location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
