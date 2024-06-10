package com.application.getgoproject.callback;

import com.application.getgoproject.models.User;

public interface UserCallback {
    void onUserFetched(User user);
}
