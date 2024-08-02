package com.application.getgoproject.callback;

import com.application.getgoproject.models.User;

import java.util.List;

public interface StoryCallback {
    void onListUserFetched(List<User> users);
}
