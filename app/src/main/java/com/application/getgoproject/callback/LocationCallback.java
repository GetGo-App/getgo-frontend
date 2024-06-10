package com.application.getgoproject.callback;

import com.application.getgoproject.models.Locations;

import java.util.List;

public interface LocationCallback {
    void onLocationsFetched(List<Locations> locations);
    void onError(Throwable throwable);

    void onOneLocationsFetched(Locations locations);
}
