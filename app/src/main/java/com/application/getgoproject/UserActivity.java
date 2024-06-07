package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.ListItemAdapter;
import com.application.getgoproject.models.ListItem;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private ImageButton imgBtnHome, imageBtnAdd, imgbtnGoback;
    private RecyclerView recyclerUser, recyclerService;
    private ListItemAdapter adapterUser, adapterService;
    private ArrayList<ListItem> arrayPersonal, arrayService;
    private int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        mapping();

        adapterUser = new ListItemAdapter(this, R.layout.layout_item_profile, arrayPersonal);
        recyclerUser.setAdapter(adapterUser);

        recyclerUser.setLayoutManager(new LinearLayoutManager(this));

        adapterService = new ListItemAdapter(this, R.layout.layout_item_profile, arrayService);
        recyclerService.setAdapter(adapterService);
        recyclerService.setLayoutManager(new LinearLayoutManager(this));

        adapterUser.setOnItemClickListener(new ListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                index = position;
                adapterUser.setSelectedPosition(position);

                ListItem clickedItem = arrayPersonal.get(position);
//                Toast.makeText(UserActivity.this, clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (clickedItem.getTitle().equals("Change Profile")){
                    Intent intent = new Intent(UserActivity.this, ChangeProfileActivity.class);
                    startActivity(intent);
                    finish();
                } else if (clickedItem.getTitle().equals("Transaction History")){
                    Intent intent = new Intent(UserActivity.this, TransactionActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        adapterService.setOnItemClickListener(new ListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                index = position;
                adapterService.setSelectedPosition(position);

                ListItem clickedItem = arrayService.get(position);
//                Toast.makeText(UserActivity.this, clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (clickedItem.getTitle().equals("Term of services")){
                    Intent intent = new Intent(UserActivity.this, TermOfServiceActivity.class);
                    startActivity(intent);
                    finish();
                } else if (clickedItem.getTitle().equals("Privacy policy")){
                    Intent intent = new Intent(UserActivity.this, PrivacyPolicyActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        imageBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar();
            }
        });
        imgBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });
    }
    private void changeAvatar() {
        Intent intent = new Intent(UserActivity.this, AvatarActivity.class);
        startActivity(intent);
        finish();
    }
    private void mapping(){
        imgBtnHome = findViewById(R.id.imageBtnHome);
        imageBtnAdd = findViewById(R.id.imageBtnAdd);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        recyclerUser = findViewById(R.id.recyclerUser);
        recyclerService = findViewById(R.id.recyclerService);

        arrayPersonal = new ArrayList<>();
        arrayPersonal.add(new ListItem(R.drawable.profile, "Change Profile",""));
        arrayPersonal.add(new ListItem(R.drawable.info, "Transaction History",""));

        arrayService = new ArrayList<>();
        arrayService.add(new ListItem(R.drawable.brightness_alert, "Term of services",""));
        arrayService.add(new ListItem(R.drawable.privacy_tip, "Privacy policy",""));
        arrayService.add(new ListItem(R.drawable.chat_info, "Support",""));
        arrayService.add(new ListItem(R.drawable.move_item, "Log out ",""));
    }
    private void homeForm(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void statusForm(){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
}
