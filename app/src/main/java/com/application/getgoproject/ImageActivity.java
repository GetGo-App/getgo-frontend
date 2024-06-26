package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.ImageAdapter;
import com.application.getgoproject.models.Image;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    private Button btnUpload, btnSave;
    private ImageView imgGoBack;
    private RecyclerView recyclerImage;
    private ImageAdapter adapter;
    private ArrayList<Image> imageArray;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);

        mapping();

        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void mapping() {
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        imgGoBack = findViewById(R.id.imgGoBack);
        recyclerImage = findViewById(R.id.recyclerImage);
    }

    private void goBack() {
        Intent intent = new Intent(this, NewStatusActivity.class);
        startActivity(intent);
        finish();
    }
}
