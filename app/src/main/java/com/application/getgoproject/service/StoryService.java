package com.application.getgoproject.service;

import com.application.getgoproject.dto.ReactStoryDTO;
import com.application.getgoproject.dto.StoryDTO;
import com.application.getgoproject.models.Story;
import com.application.getgoproject.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StoryService {

    @POST("stories")
    Call<ResponseBody> createNewStory(@Body StoryDTO storyDTO, @Header("Authorization") String token);

    @GET("stories")
    Call<List<Story>> getAllStory(@Header("Authorization") String token);

    @GET("stories/user/{userId}")
    Call<List<Story>> getUserStory(@Path("userId") String userId, @Header("Authorization") String token);

    @PATCH("stories/{id}/reactions")
    Call<ResponseBody> updateStoryReaction(@Path("id") String id, @Body ReactStoryDTO reactStoryDTO, @Header("Authorization") String token);

    @GET("stories/{id}/reacted-users")
    Call<List<User>> getReactedUsers(@Path("id") String id, @Header("Authorization") String token);

    @DELETE("stories")
    Call<ResponseBody> deleteStory(@Query("id") String id, @Header("Authorization") String token);
}
