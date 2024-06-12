package com.application.getgoproject.models;

import java.util.List;

public class ChatAgentMessage {
    private List<Integer> ids_location;

    private String text;

    public ChatAgentMessage(List<Integer> ids_location, String text) {
        this.ids_location = ids_location;
        this.text = text;
    }

    public List<Integer> getIds_location() {
        return ids_location;
    }

    public void setIds_location(List<Integer> ids_location) {
        this.ids_location = ids_location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
