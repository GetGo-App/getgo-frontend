package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.AvatarAdapter;
import com.application.getgoproject.models.Avatar;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AvatarActivity extends AppCompatActivity {
    private Button btnUpload, btnSave;

    private ShapeableImageView avatar;
    private ImageView imgbtnGoback;
    private ArrayList<Avatar> arrayAvatar;
    private AvatarAdapter avatarAdapter;
    private RecyclerView recyclerAvatar;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avatar_dialog);

        mapping();

        avatarAdapter = new AvatarAdapter(this, R.layout.layout_avatar, arrayAvatar);
        recyclerAvatar.setAdapter(avatarAdapter);
        recyclerAvatar.setLayoutManager(new GridLayoutManager(this,2));

        avatarAdapter.setOnItemClickListener(new AvatarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                index = position;
                avatarAdapter.setSelectedPosition(position);
//
//                Avatar clickedItem = arrayAvatar.get(position);
                Toast.makeText(AvatarActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvatarActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void mapping() {
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        avatar = findViewById(R.id.avatar);
        recyclerAvatar = findViewById(R.id.recyclerAvatar);

        arrayAvatar = new ArrayList<>();
        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
        arrayAvatar.add(new Avatar(R.drawable.startbackground));
        arrayAvatar.add(new Avatar(R.drawable.startbackground));
        arrayAvatar.add(new Avatar(R.drawable.startbackground));
        arrayAvatar.add(new Avatar(R.drawable.startbackground));
        arrayAvatar.add(new Avatar(R.drawable.sapa));
        arrayAvatar.add(new Avatar(R.drawable.sapa));
        arrayAvatar.add(new Avatar(R.drawable.sapa));
        arrayAvatar.add(new Avatar(R.drawable.startbackground));
        arrayAvatar.add(new Avatar(R.drawable.sapabackground));
        arrayAvatar.add(new Avatar(R.drawable.sapa));

    }
}
