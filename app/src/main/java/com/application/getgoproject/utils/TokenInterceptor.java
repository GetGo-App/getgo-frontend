package com.application.getgoproject.utils;

import com.application.getgoproject.models.UserAuthentication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private SharedPrefManager sharedPrefManager;

    public TokenInterceptor(SharedPrefManager sharedPrefManager) {
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        UserAuthentication userAuthentication = sharedPrefManager.getUser();
        if (userAuthentication != null && userAuthentication.getAccessToken() != null) {
            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + userAuthentication.getAccessToken());
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(originalRequest);
    }
}
