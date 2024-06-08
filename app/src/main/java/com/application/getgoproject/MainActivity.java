package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.utils.SharedPrefManager;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private ShapeableImageView avatar;
    private ImageView imgPlace, imgBanner;
    private ImageButton btnAssistant, btnStatus, btnQr, imgAddStory;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mapping();

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        if (userAuthentication != null) {
            String username = userAuthentication.getUsername();
            tvUsername.setText(username);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            // Optionally redirect to login screen
        }

        btnAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatBoxActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrForm();
            }
        });

        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageForm();
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });
        imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationListForm();
            }
        });
        imgAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraForm();
            }
        });
    }
    private void mapping(){
        avatar = findViewById(R.id.avatar);
        imgPlace = findViewById(R.id.imgPlace);
        imgBanner = findViewById(R.id.imgBanner);
        btnAssistant = findViewById(R.id.btnAssistant);
        btnStatus = findViewById(R.id.btnStatus);
        btnQr = findViewById(R.id.btnQr);
        imgAddStory =findViewById(R.id.imgAddStory);
        tvUsername = findViewById(R.id.username);
    }
    private void packageForm(){
        Intent intent = new Intent(this, PackageActivity.class);
        startActivity(intent);
        finish();
    }
    private void qrForm(){
        Intent intent = new Intent(this, QRcodeActivity.class);
        startActivity(intent);
        finish();
    }
    private void statusForm(){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
    private void locationListForm(){
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
        finish();
    }
    private void userForm(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
    private void cameraForm(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
        finish();
    }
}