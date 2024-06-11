package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.ChatBoxAdapter;
import com.application.getgoproject.adapter.LocationChatBoxAdapter;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.models.ChatAgentMessage;
import com.application.getgoproject.models.ChatBox;
import com.application.getgoproject.models.Locations;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.ChatAgentService;
import com.application.getgoproject.service.LocationService;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatBoxActivity extends AppCompatActivity {

    private ChatAgentService chatAgentService;
    private UserService userService;
    private LocationService locationService;
    private String userId;
    private UserAuthentication userAuthentication;
    private String userToken;

    private ImageButton buttonBack;
    private RecyclerView recyclerView;
    private ChatBoxAdapter chatBoxAdapter;
    private List<ChatBox> chatBoxList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);

        userAuthentication = SharedPrefManager.getInstance(this).getUser();
        userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);
        chatAgentService = retrofit.create(ChatAgentService.class);
        locationService = retrofit.create(LocationService.class);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        chatBoxList = new ArrayList<>();
        chatBoxList.add(new ChatBox("Hi! Where do you wanna go today?", false));

        chatBoxAdapter = new ChatBoxAdapter(chatBoxList, new LocationChatBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Locations location) {
                Intent intent = new Intent(ChatBoxActivity.this, DetailLocationActivity.class);
                intent.putExtra("detail location", location.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(chatBoxAdapter);

        buttonBack = findViewById(R.id.buttonBack);
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        ImageButton sendButton = findViewById(R.id.buttonSend);

        getUserByUsername(userAuthentication.getUsername(), userToken, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                userId = user.getId();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = editTextMessage.getText().toString().trim();
                if (!question.isEmpty()) {
                    chatBoxList.add(new ChatBox(question));
                    chatBoxAdapter.notifyItemInserted(chatBoxList.size() - 1);
                    recyclerView.scrollToPosition(chatBoxList.size() - 1);

                    editTextMessage.setText("");

                    sendMessageToAgent(question, userId, userToken);
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });

    }

    private void homeForm() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getUserByUsername(String username, String token, UserCallback callback) {
        Call<User> call = userService.getUserByUsername(username, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    callback.onUserFetched(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                // Handle failure
            }
        });
    }

    public void sendMessageToAgent(String question, String userId, String token) {
        Call<ChatAgentMessage> call = chatAgentService.sendMessageToAgent(question, userId, token);
        call.enqueue(new Callback<ChatAgentMessage>() {
            @Override
            public void onResponse(Call<ChatAgentMessage> call, Response<ChatAgentMessage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChatAgentMessage chatAgentMessage = response.body();

                    if (chatAgentMessage.getMessage() != null) {
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chatBoxList.add(new ChatBox(chatAgentMessage.getMessage(), false));
                                chatBoxAdapter.notifyItemInserted(chatBoxList.size() - 1);
                                recyclerView.scrollToPosition(chatBoxList.size() - 1);
                            }
                        }, 1000);
                    }

                    if (chatAgentMessage.getIds_location() != null && !chatAgentMessage.getIds_location().isEmpty()) {
                        fetchLocationsByIds(chatAgentMessage.getIds_location(), userToken);
                    }
                } else {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatBoxList.add(new ChatBox("Sorry, I have no idea!", false));
                            chatBoxAdapter.notifyItemInserted(chatBoxList.size() - 1);
                            recyclerView.scrollToPosition(chatBoxList.size() - 1);
                        }
                    }, 1000);
                }
            }

            @Override
            public void onFailure(Call<ChatAgentMessage> call, Throwable throwable) {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Error", throwable.getMessage());
                        chatBoxList.add(new ChatBox("Sorry, an error has been occurred", false));
                        chatBoxAdapter.notifyItemInserted(chatBoxList.size() - 1);
                        recyclerView.scrollToPosition(chatBoxList.size() - 1);
                    }
                }, 1000);
            }
        });
    }

    private void fetchLocationsByIds(List<Integer> ids, String token) {
        for (Integer id : ids) {
            Call<Locations> call = locationService.getLocationsById(id, token);
            call.enqueue(new Callback<Locations>() {
                @Override
                public void onResponse(Call<Locations> call, Response<Locations> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Locations location = response.body();
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chatBoxList.add(new ChatBox(location));
                                chatBoxAdapter.notifyItemInserted(chatBoxList.size() - 1);
                                recyclerView.scrollToPosition(chatBoxList.size() - 1);
                            }
                        }, 1000);
                    }
                }

                @Override
                public void onFailure(Call<Locations> call, Throwable throwable) {
                    // Handle failure
                }
            });
        }
    }
}
