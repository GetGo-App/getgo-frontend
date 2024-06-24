package com.application.getgoproject.callback;

import com.application.getgoproject.models.LocationComment;

import java.util.List;

public interface LocationCommentCallback {
    void onLocationCommentsFetched(List<LocationComment> locationComments);
    void onError(Throwable throwable);
}
