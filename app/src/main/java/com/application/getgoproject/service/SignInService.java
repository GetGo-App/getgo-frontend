package com.application.getgoproject.service;

import com.application.getgoproject.dto.SignInDto;
import com.application.getgoproject.models.UserAuthentication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInService {

    @POST("sign-in")
    Call<UserAuthentication> signInUser(@Body SignInDto signInDto);
}
