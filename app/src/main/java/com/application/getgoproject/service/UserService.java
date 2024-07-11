package com.application.getgoproject.service;

import com.application.getgoproject.dto.AvatarDTO;
import com.application.getgoproject.dto.UpdateUserDTO;
import com.application.getgoproject.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface UserService {

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}/info")
    Call<User> getUserByUserId(@Path("id") String id, @Header("Authorization") String token);

    @GET("users/{username}")
    Call<User> getUserByUsername(@Path("username") String username, @Header("Authorization") String authorization);

    @PATCH("users/{username}")
    Call<ResponseBody> updateUserByUsername(@Path("username") String username, @Body UpdateUserDTO updateUserDTO, @Header("Authorization") String token);

    @PATCH("users/{username}")
    Call<ResponseBody> updateAvatarByUsername(@Path("username") String username, @Body AvatarDTO avatarDTO, @Header("Authorization") String token);
}
