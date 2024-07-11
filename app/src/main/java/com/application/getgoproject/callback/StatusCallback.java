package com.application.getgoproject.callback;

import com.application.getgoproject.models.Status;

import java.util.List;

public interface StatusCallback {
    void onListStatusFetched(List<Status> statuses);
    void onError(Throwable throwable);
}
