package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

public class NewStatusActivity extends AppCompatActivity {
    private ImageButton imgbtnGoback;
    private Button btnUpload;
    private TextInputEditText contentStatus;
    private TextView username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status);

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();

        mapping();
        username.setText(userAuthentication.getUsername());

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewStatusActivity.this, StatusActivity.class);
                intent.putExtra("content status","dang truyen");
                startActivity(intent);
            }
        });
    }
    private void statusForm(){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
    private void mapping(){
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        btnUpload = findViewById(R.id.btnComment);
        contentStatus = findViewById(R.id.contentStatus);
        username = findViewById(R.id.username);

    }
}
