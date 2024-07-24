package com.application.getgoproject.service;

import com.application.getgoproject.models.ChatAgentMessage;
import com.application.getgoproject.models.ChatAgentMessageHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatAgentService {

    @POST("messages/chat-agent")
    Call<ChatAgentMessage> sendMessageToAgent(@Query("question") String question, @Query("userId") String userId, @Header("Authorization") String token);

    @GET("messages/chat-agent/history")
    Call<List<ChatAgentMessageHistory>> getAgentMessageHistory(@Query("userId") String userId, @Header("Authorization") String token);
}
