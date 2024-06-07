package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.ImageAdapter;
import com.application.getgoproject.adapter.MessageAdapter;
import com.application.getgoproject.adapter.StatusAdapter;
import com.application.getgoproject.models.Image;
import com.application.getgoproject.models.Message;
import com.application.getgoproject.models.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity {
    private TextView userStatusNumber, userFriendNumber;
    private ImageButton imgbtnGoback;
    private FrameLayout statusLayout;
    private Button tabStatus, tabImage, tabMessage, btnAddStatus, btnSettingAccount;
    private List<Image> imageList;
    private List<Message> messageList;
    private List<Status> statusList;
    private String sizeStatus, sizeFriend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mapping();

        addLayoutStatus();
        sizeStatus = statusList.size()+ "";
        userStatusNumber.setText(sizeStatus);

        sizeFriend = statusList.size()+ "";
        userFriendNumber.setText(sizeFriend);
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });
        btnAddStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });

        btnSettingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingAccount();
            }
        });
        tabStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLayoutStatus();
            }
        });

        tabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLayoutImage();
            }
        });

        tabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLayoutMessage();
            }
        });
    }

    private void mapping() {
        statusLayout = findViewById(R.id.statusLayout);
        tabStatus = findViewById(R.id.tabStatus);
        tabImage = findViewById(R.id.tabImage);
        tabMessage = findViewById(R.id.tabMessage);
        userStatusNumber =findViewById(R.id.userStatusNumber);
        userFriendNumber= findViewById(R.id.userFriendNumber);
        imgbtnGoback= findViewById(R.id.imgbtnGoback);
        btnAddStatus = findViewById(R.id.btnAddStatus);
        btnSettingAccount = findViewById(R.id.btnSettingAccount);
    }
    private void settingAccount(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
    private void homeForm(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void statusForm(){
        Intent intent = new Intent(this, NewStatusActivity.class);
        startActivity(intent);
        finish();
    }
    private void addLayoutStatus() {
        statusLayout.removeAllViewsInLayout();

        tabStatus.setBackgroundResource(R.drawable.underline_button);
        tabStatus.setTextColor(getColor(R.color.black));

        tabImage.setBackgroundResource(R.drawable.not_underline_button);
        tabImage.setTextColor(Color.parseColor("#858585"));

        tabMessage.setBackgroundResource(R.drawable.not_underline_button);
        tabMessage.setTextColor(Color.parseColor("#858585"));

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

    private void addLayoutImage() {
        statusLayout.removeAllViewsInLayout();

        tabStatus.setBackgroundResource(R.drawable.not_underline_button);
        tabStatus.setTextColor(Color.parseColor("#858585"));


        tabImage.setBackgroundResource(R.drawable.underline_button);
        tabImage.setTextColor(getColor(R.color.black));

        tabMessage.setBackgroundResource(R.drawable.not_underline_button);
        tabMessage.setTextColor(Color.parseColor("#858585"));

        imageList = new ArrayList<>();

        imageList.add(new Image(R.drawable.sapabackground));
        imageList.add(new Image(R.drawable.sapa));
        imageList.add(new Image(R.drawable.startbackground));
        imageList.add(new Image(R.drawable.startbackground));
        imageList.add(new Image(R.drawable.startbackground));
        imageList.add(new Image(R.drawable.startbackground));
        imageList.add(new Image(R.drawable.startbackground));
        imageList.add(new Image(R.drawable.startbackground));
        imageList.add(new Image(R.drawable.startbackground));

        LayoutInflater inflater = LayoutInflater.from(this);
        View layoutImage = inflater.inflate(R.layout.layout_image, statusLayout, false);

        GridView gridView = layoutImage.findViewById(R.id.listImage);
        ImageAdapter imageAdapter = new ImageAdapter(this, imageList);
        gridView.setAdapter(imageAdapter);

        statusLayout.addView(layoutImage);
    }

    private void addLayoutMessage() {
        statusLayout.removeAllViewsInLayout();

        tabStatus.setBackgroundResource(R.drawable.not_underline_button);
        tabStatus.setTextColor(Color.parseColor("#858585"));

        tabImage.setBackgroundResource(R.drawable.not_underline_button);
        tabImage.setTextColor(Color.parseColor("#858585"));

        tabMessage.setBackgroundResource(R.drawable.underline_button);
        tabMessage.setTextColor(getColor(R.color.black));

        messageList = new ArrayList<>();

        messageList.add(new Message("Phan Hieu Nghia", "met qua", "2m", R.drawable.avatar));
        messageList.add(new Message("Ngo Ngo", "ngu di", "5m", R.drawable.avatar));
        messageList.add(new Message("Phan Van Phuc", "nope", "10m", R.drawable.avatar));
        messageList.add(new Message("Mai Mai", "ehe", "12m", R.drawable.avatar));
        messageList.add(new Message("Phan Hieu Nghia", "met qua", "2m", R.drawable.avatar));
        messageList.add(new Message("Ngo Ngo", "ngu di", "5m", R.drawable.avatar));
        messageList.add(new Message("Phan Van Phuc", "nope", "10m", R.drawable.avatar));
        messageList.add(new Message("Mai Mai", "ehe", "12m", R.drawable.avatar));
        messageList.add(new Message("Phan Hieu Nghia", "met qua", "2m", R.drawable.avatar));
        messageList.add(new Message("Ngo Ngo", "ngu di", "5m", R.drawable.avatar));
        messageList.add(new Message("Phan Van Phuc", "nope", "10m", R.drawable.avatar));
        messageList.add(new Message("Mai Mai", "ehe", "12m", R.drawable.avatar));

        LayoutInflater inflater = LayoutInflater.from(this);
        View layoutMessage = inflater.inflate(R.layout.layout_message, statusLayout, false);

        ListView listViewMessage = layoutMessage.findViewById(R.id.listMessage);
        MessageAdapter messageAdapter = new MessageAdapter(this, messageList);
        listViewMessage.setAdapter(messageAdapter);

        statusLayout.addView(layoutMessage);
    }

}
