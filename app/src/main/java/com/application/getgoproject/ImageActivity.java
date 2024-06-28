package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.ImageAdapter;
import com.application.getgoproject.adapter.NewStatusImageAdapter;
import com.application.getgoproject.models.Image;
import com.application.getgoproject.models.ImageNewStatus;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    private Button btnUpload, btnAdd;
    private ImageView imgGoBack;
    private RecyclerView recyclerImage;
    private NewStatusImageAdapter adapter;
    private ArrayList<ImageNewStatus> imageArray;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);

        mapping();

        adapter = new NewStatusImageAdapter(this, R.layout.layout_image_newstatus, imageArray);
        recyclerImage.setAdapter(adapter);
        recyclerImage.setLayoutManager(new GridLayoutManager(this, 2));

        Toast.makeText(this,imageArray.size() + "", Toast.LENGTH_SHORT).show();
        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadAction();
            }
        });
    }

    private void mapping() {
        btnUpload = findViewById(R.id.btnUpload);
        btnAdd = findViewById(R.id.btnAdd);
        imgGoBack = findViewById(R.id.imgGoBack);
        recyclerImage = findViewById(R.id.recyclerImage);

        imageArray = new ArrayList<>();

        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
        imageArray.add(new ImageNewStatus(R.drawable.sapa));
    }

    private void goBack() {
        Intent intent = new Intent(this, NewStatusActivity.class);
        startActivity(intent);
        finish();
    }

    private void upLoadAction() {
        Toast.makeText(this, "upload action", Toast.LENGTH_SHORT).show();
    }

    private void addAction() {
        Toast.makeText(this, "add action", Toast.LENGTH_SHORT).show();
    }
}
