package com.application.getgoproject.service;

import com.application.getgoproject.dto.GoogleAuthenticationDto;
import com.application.getgoproject.dto.SignUpDto;
import com.application.getgoproject.models.UserAuthentication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GoogleAuthenticationService {
    @POST("google-auth")
    Call<UserAuthentication> googleAuth(@Body GoogleAuthenticationDto googleAuthenticationDto);
}
