package com.application.getgoproject.service;

import com.application.getgoproject.dto.CommentDTO;
import com.application.getgoproject.models.Comment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CommentService {

    @POST("comments")
    Call<Comment> createComments(@Body CommentDTO commentDTO, @Header("Authorization") String token);
}
