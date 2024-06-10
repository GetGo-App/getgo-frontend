package com.application.getgoproject.service;

import com.application.getgoproject.models.ChatAgentMessage;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatAgentService {

    @POST("messages/chat-agent")
    Call<ChatAgentMessage> sendMessageToAgent(@Query("question") String question, @Query("userId") String userId, @Header("Authorization") String token);
}
