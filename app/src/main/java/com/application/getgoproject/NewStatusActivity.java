package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.processing.SurfaceProcessorNode;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.application.getgoproject.adapter.NewStatusImageAdapter;
import com.application.getgoproject.models.ImageNewStatus;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class NewStatusActivity extends AppCompatActivity {
    private ImageButton imgbtnGoback, imgAddImage, imgCamera;
    private Button btnUpload;
    private TextInputEditText contentStatus;
    private TextView username;
    private RecyclerView recyclerImage;
    private NewStatusImageAdapter adapter;
    private ArrayList<ImageNewStatus> imageArray;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status);

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();

        mapping();
        username.setText(userAuthentication.getUsername());

        adapter = new NewStatusImageAdapter(this, R.layout.layout_image_newstatus, imageArray);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);

        recyclerImage.setAdapter(adapter);
        recyclerImage.setLayoutManager(gridLayoutManager);

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });

        imgAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });

//        imgCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cameraForm();
//            }
//        });

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

    private void addImage() {

    }

//    private void cameraForm() {
//        Intent intent = new Intent(this, CameraActivity.class);
//        startActivity(intent);
//        finish();
//    }
    private void mapping(){
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        imgAddImage = findViewById(R.id.imgAddImage);
//        imgCamera = findViewById(R.id.imgCamera);
        btnUpload = findViewById(R.id.btnUpload);
        contentStatus = findViewById(R.id.contentStatus);
        username = findViewById(R.id.username);

        recyclerImage = findViewById(R.id.recyclerImage);

        imageArray = new ArrayList<>();

        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
    }
}
