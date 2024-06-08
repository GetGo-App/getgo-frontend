package com.application.getgoproject.service;

import com.application.getgoproject.dto.SignUpDto;
import com.application.getgoproject.models.UserAuthentication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {

    @POST("sign-up")
    Call<UserAuthentication> signUpUser(@Body SignUpDto signUpDTO);
}
