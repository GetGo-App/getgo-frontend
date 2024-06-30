package com.application.getgoproject.service;

import com.application.getgoproject.dto.StatusDTO;
import com.application.getgoproject.models.Status;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StatusService {

    @GET("statuses/user/{userId}")
    Call<List<Status>> getStatusByUserId(@Path("userId") String userId, @Header("Authorization") String token);

    @GET("statuses")
    Call<List<Status>> getAllStatus(@Header("Authorization") String token);

    @POST("statuses")
    Call<ResponseBody> createStatus(@Body StatusDTO statusDTO, @Header("Authorization") String token);
}
