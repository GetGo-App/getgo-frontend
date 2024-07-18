package com.application.getgoproject.models;

import java.util.List;

public class ChatBox {
    private String text;
    private List<Locations> location;
    private String locationMessage;
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

    public ChatBox(List<Locations> location) {
        this.text = null;
        this.isSent = false; // Received message
        this.location = location;
    }

    public ChatBox(String text, List<Locations> location) {
        this.text = text;
        this.isSent = false; // Received message
        this.location = location;
    }

    public ChatBox(String text, String locationMessage, List<Locations> location) {
        this.text = text;
        this.isSent = false; // Received message
        this.locationMessage = locationMessage;
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public List<Locations> getLocation() {
        return location;
    }

    public String getLocationMessage() {
        return locationMessage;
    }

    public void setLocationMessage(String locationMessage) {
        this.locationMessage = locationMessage;
    }

    public boolean isSent() {
        return isSent;
    }
}
