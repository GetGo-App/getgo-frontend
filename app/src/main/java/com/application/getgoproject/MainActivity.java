package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private ShapeableImageView avatar;
    private ImageView imgPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        anhXa();

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userForm();
            }
        });
        imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmtForm();
            }
        });
    }
    private void anhXa(){
        avatar = findViewById(R.id.avatar);
        imgPlace = findViewById(R.id.imgPlace);
    }
    private void cmtForm(){
        Intent intent = new Intent(this, CommentActivity.class);
        startActivity(intent);
        finish();
    }
    private void userForm(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
}