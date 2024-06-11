package com.application.getgoproject.models;

public class ChatBox {
    private String text;
    private Locations location;
    private boolean isSent;

    public ChatBox(String text) {
        this.text = text;
        this.isSent = true;
        this.location = null;
    }

    public ChatBox(String text, boolean isSent) {
        this.text = text;
        this.isSent = isSent;
        this.location = null;
    }

    public ChatBox(Locations location) {
        this.text = null;
        this.isSent = false; // Received message
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public Locations getLocation() {
        return location;
    }

    public boolean isSent() {
        return isSent;
    }
}
