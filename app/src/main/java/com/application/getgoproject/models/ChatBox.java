package com.application.getgoproject.models;

public class ChatBox {
    private String text;
    private boolean isSent;

    public ChatBox(String text, boolean isSent) {
        this.text = text;
        this.isSent = isSent;
    }

    public String getText() {
        return text;
    }

    public boolean isSent() {
        return isSent;
    }
}
