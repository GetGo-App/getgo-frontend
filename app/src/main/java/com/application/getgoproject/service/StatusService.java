package com.application.getgoproject.service;

import com.application.getgoproject.models.Status;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface StatusService {

    @GET("statuses/user/{userId}")
    Call<List<Status>> getStatusByUserId(@Path("userId") String userId, @Header("Authorization") String token);
}
