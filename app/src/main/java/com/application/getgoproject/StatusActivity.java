package com.application.getgoproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.models.Image;
import com.application.getgoproject.models.Message;
import com.application.getgoproject.models.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity {

    private FrameLayout statusLayout;
    private Button tabStatus, tabImage, tabMessage;
    private LayoutInflater childLayout;
    private List<Image> imageList;
    private List<Message> messageList;
    private List<Status> statusList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        childLayout = LayoutInflater.from(StatusActivity.this);
        mapping();

        addLayoutStatus();

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
    }

    private void addLayoutStatus() {
        statusLayout.removeAllViewsInLayout();

        LayoutInflater inflater = LayoutInflater.from(this);
        View layoutStatus = inflater.inflate(R.layout.layout_status, statusLayout, false);

        statusList = new ArrayList<>();

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.sapabackground);
        arrayList.add(R.drawable.sapa);

        ArrayList<Integer> arrayList2 = new ArrayList<>();
        arrayList2.add(R.drawable.sapa);
        arrayList2.add(R.drawable.sapabackground);
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

        messageList = new ArrayList<>();

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
