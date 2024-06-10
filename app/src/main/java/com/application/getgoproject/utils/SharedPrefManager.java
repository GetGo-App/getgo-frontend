package com.application.getgoproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_ACCESS_TOKEN = "key_access_token";
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_IS_ACTIVE = "key_is_active";

    private static SharedPrefManager instance;
    private static Context mContext;

    public SharedPrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUser(UserAuthentication userAuthentication) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, userAuthentication.getAccessToken());
        editor.putString(KEY_USERNAME, JwtUtils.getUsernameFromToken(userAuthentication.getAccessToken()));
        editor.putString(KEY_EMAIL, userAuthentication.getEmail());
        editor.putBoolean(KEY_IS_ACTIVE, userAuthentication.isActive());
        editor.apply();
    }

    public UserAuthentication getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserAuthentication(
                sharedPreferences.getString(KEY_ACCESS_TOKEN, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getBoolean(KEY_IS_ACTIVE, false)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
