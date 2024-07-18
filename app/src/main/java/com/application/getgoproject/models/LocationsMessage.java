package com.application.getgoproject.models;

import java.util.List;

public class LocationsMessage {
    private List<Integer> locations;
    private String message;

    public LocationsMessage(List<Integer> locations, String message) {
        this.locations = locations;
        this.message = message;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public void setLocations(List<Integer> locations) {
        this.locations = locations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
