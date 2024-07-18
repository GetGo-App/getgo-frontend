package com.application.getgoproject.models;

public class ChatAgentMessage {
    private String texts_message;
    private LocationsMessage locations_message;

    public ChatAgentMessage(String texts_message, LocationsMessage locations_message) {
        this.texts_message = texts_message;
        this.locations_message = locations_message;
    }

    public String getTexts_message() {
        return texts_message;
    }

    public void setTexts_message(String texts_message) {
        this.texts_message = texts_message;
    }

    public LocationsMessage getLocations_message() {
        return locations_message;
    }

    public void setLocations_message(LocationsMessage locations_message) {
        this.locations_message = locations_message;
    }
}
