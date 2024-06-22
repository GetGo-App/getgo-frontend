package com.application.getgoproject.service;

import com.application.getgoproject.dto.CommentDTO;
import com.application.getgoproject.models.Comment;
import com.application.getgoproject.models.LocationComment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CommentService {

    @POST("comments")
    Call<ResponseBody> createComments(@Body CommentDTO commentDTO, @Header("Authorization") String token);
}
