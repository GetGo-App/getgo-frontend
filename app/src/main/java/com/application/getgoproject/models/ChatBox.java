package com.application.getgoproject.models;

import java.util.List;

public class ChatBox {
    private String text;
    private List<Locations> location;
    private String locationMessage;
    private boolean isSent;
    private boolean isTypingIndicator;

    // Constructor for sent text message
    public ChatBox(String text) {
        this.text = text;
        this.isSent = true;
        this.location = null;
        this.isTypingIndicator = false;
    }

    // Constructor for sent or received text message
    public ChatBox(String text, boolean isSent) {
        this.text = text;
        this.isSent = isSent;
        this.location = null;
        this.isTypingIndicator = false;
    }

    // Constructor for received location message
    public ChatBox(List<Locations> location) {
        this.text = null;
        this.isSent = false;
        this.location = location;
        this.isTypingIndicator = false;
    }

    // Constructor for received text and location message
    public ChatBox(String text, List<Locations> location) {
        this.text = text;
        this.isSent = false;
        this.location = location;
        this.isTypingIndicator = false;
    }

    // Constructor for received text, location message, and location list
    public ChatBox(String text, String locationMessage, List<Locations> location) {
        this.text = text;
        this.isSent = false;
        this.locationMessage = locationMessage;
        this.location = location;
        this.isTypingIndicator = false;
    }

    // Static factory method for creating a typing indicator
    public static ChatBox createTypingIndicator() {
        ChatBox chatBox = new ChatBox("", false);
        chatBox.isTypingIndicator = true;
        return chatBox;
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

    public boolean isTypingIndicator() {
        return isTypingIndicator;
    }
}
