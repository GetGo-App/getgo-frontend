package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TermOfServiceActivity extends AppCompatActivity {
    private ImageButton imgbtnGoback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_of_service);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermOfServiceActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
