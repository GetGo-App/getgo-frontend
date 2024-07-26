package com.application.getgoproject.callback;

import com.application.getgoproject.models.Locations;

import java.util.List;

public interface HistoryMessageCallback {
    void onLocationsFetched(List<Locations> locations, String textMessage, String locationMessage);
}
