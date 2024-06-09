package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.StatusAdapter;
import com.application.getgoproject.models.Status;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ListStatusActivity extends AppCompatActivity {
    private ImageButton imgbtnGoback;
    private ShapeableImageView avatar;
    private FrameLayout statusLayout;
    private List<Status> statusList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_status);

        mapping();

        addLayoutStatus();
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goStatusIndivisual();
            }
        });
    }
    private void mapping() {
        avatar = findViewById(R.id.avatar);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        statusLayout = findViewById(R.id.statusLayout);
    }
    private void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void goStatusIndivisual() {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
    private void addLayoutStatus() {
        statusLayout.removeAllViewsInLayout();

        LayoutInflater inflater = LayoutInflater.from(this);
        View layoutStatus = inflater.inflate(R.layout.layout_status, statusLayout, false);

        statusList = new ArrayList<>();

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.sapa);
        arrayList.add(R.drawable.sapabackground);
        arrayList.add(R.drawable.sapabackground);
        arrayList.add(R.drawable.sapa);
        arrayList.add(R.drawable.sapabackground);
        arrayList.add(R.drawable.sapa);

        ArrayList<Integer> arrayList2 = new ArrayList<>();
        arrayList2.add(R.drawable.sapa);

        statusList.add(new Status("Phan Hieu Nghia", "The Good Place", "This is the beautiful place", "4 days ago", R.drawable.avatar, arrayList));
        statusList.add(new Status("Hoang Le Huong", "The New Place", "This is the good place", "4 days ago", R.drawable.avatar, arrayList2));

        StatusAdapter statusAdapter = new StatusAdapter(this, statusList);
        RecyclerView recyclerView = layoutStatus.findViewById(R.id.image_status_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(statusAdapter);

        statusLayout.addView(layoutStatus);
    }
}
