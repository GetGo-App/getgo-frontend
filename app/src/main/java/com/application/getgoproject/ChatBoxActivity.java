package com.application.getgoproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.models.ChatBox;

import java.util.ArrayList;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatBoxAdapter chatBoxAdapter;
    private List<ChatBox> chatBoxList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        chatBoxList = new ArrayList<>();
        chatBoxList.add(new ChatBox("Hi! Where do you wanna go today?", false));

        chatBoxAdapter = new ChatBoxAdapter(chatBoxList);
        recyclerView.setAdapter(chatBoxAdapter);

        EditText editTextMessage = findViewById(R.id.editTextMessage);
        ImageButton sendButton = findViewById(R.id.buttonSend);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    chatBoxList.add(new ChatBox(messageText, true));
                    chatBoxAdapter.notifyItemInserted(chatBoxList.size() - 1);
                    recyclerView.scrollToPosition(chatBoxList.size() - 1);

                    editTextMessage.setText("");

                    simulateReceivedMessage();
                }
            }
        });
    }

    private void simulateReceivedMessage() {
        // Simulate a delay before adding the received message
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatBoxList.add(new ChatBox("I will help you!", false));
                chatBoxAdapter.notifyItemInserted(chatBoxList.size() - 1);
                recyclerView.scrollToPosition(chatBoxList.size() - 1);
            }
        }, 1000);
    }
}