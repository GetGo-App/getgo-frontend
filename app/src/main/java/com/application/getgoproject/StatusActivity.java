package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.ImageAdapter;
import com.application.getgoproject.adapter.MessageAdapter;
import com.application.getgoproject.adapter.StatusAdapter;
import com.application.getgoproject.callback.StatusCallback;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.models.Image;
import com.application.getgoproject.models.Message;
import com.application.getgoproject.models.Status;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.StatusService;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatusActivity extends AppCompatActivity {

    private UserService userService;
    private StatusService statusService;
    private UserAuthentication userAuthentication;

    private ShapeableImageView avatar;
    private TextView userStatusNumber, userFriendNumber, username;
    private ImageButton imgbtnGoback;
    private FrameLayout statusLayout;
    private Button tabStatus, tabImage, tabMessage, btnAddStatus;
    private List<Image> imageList;
    private List<Message> messageList;
    private List<Status> statusList;
    private String sizeStatus, sizeFriend, userToken, userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        userAuthentication = SharedPrefManager.getInstance(this).getUser();
        userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        statusService = retrofit.create(StatusService.class);
        userService = retrofit.create(UserService.class);

        mapping();

        username.setText(userAuthentication.getUsername());

        getUserByName(userAuthentication.getUsername(), userToken, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                userId = user.getId();
                userFriendNumber.setText(user.getFriends() != null ? user.getFriends().size() + "" : "0");

                if (user.getAvatar() != null) {
                    Glide.with(StatusActivity.this)
                            .load(user.getAvatar())
                            .into(avatar);
                }
                else {
                    avatar.setImageResource(R.drawable.avatar);
                }

                addLayoutStatus();
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listStatusForm();
            }
        });
        btnAddStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
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
                Toast.makeText(StatusActivity.this, "Developing...", Toast.LENGTH_LONG).show();
//                addLayoutMessage();
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
        username = findViewById(R.id.username);
        avatar = findViewById(R.id.avatar);
    }
    private void settingAccount(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
    private void listStatusForm(){
        Intent intent = new Intent(this, ListStatusActivity.class);
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

        StatusAdapter statusAdapter = new StatusAdapter(this, statusList, userToken);

        getStatusByUserId(userId, userToken, new StatusCallback() {
            @Override
            public void onListStatusFetched(List<Status> statuses) {
                statusList.clear();
                for (Status status : statuses) {
                    statusList.add(new Status(status.getUploader(), status.getContent(),
                            status.getUploadedTime(), status.getPrivacyMode(),
                            status.getImages(), status.getReactedUsers()));
                }

                sizeStatus = statusList.size()+ "";
                userStatusNumber.setText(sizeStatus);
                statusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("Failed to get status", throwable.getMessage());
            }
        });

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

//        imageList.add(new Image(R.drawable.sapabackground));
//        imageList.add(new Image(R.drawable.sapa));
//        imageList.add(new Image(R.drawable.startbackground));
//        imageList.add(new Image(R.drawable.startbackground));
//        imageList.add(new Image(R.drawable.startbackground));
//        imageList.add(new Image(R.drawable.startbackground));
//        imageList.add(new Image(R.drawable.startbackground));
//        imageList.add(new Image(R.drawable.startbackground));
//        imageList.add(new Image(R.drawable.startbackground));

        LayoutInflater inflater = LayoutInflater.from(StatusActivity.this);
        View layoutImage = inflater.inflate(R.layout.layout_image, statusLayout, false);

        GridView gridView = layoutImage.findViewById(R.id.listImage);
        ImageAdapter imageAdapter = new ImageAdapter(this, imageList);
        gridView.setAdapter(imageAdapter);

        statusLayout.addView(layoutImage);

        getStatusByUserId(userId, userToken, new StatusCallback() {
            @Override
            public void onListStatusFetched(List<Status> statuses) {
                for (Status status : statuses) {
                    List<String> images = status.getImages();
                    if (images != null) {
                        for (String imageUrl : images) {
                            imageList.add(new Image(imageUrl));
                        }
                    }
                }

                // Notify the adapter of the data change on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle error
            }
        });
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

    private void getUserByName(String username, String token, UserCallback callback) {
        Call<User> call = userService.getUserByUsername(username, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    callback.onUserFetched(user);
                }
                else {
                    Log.d("Error", "Failed to fetch");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
    }

    private void getStatusByUserId(String userId, String token, StatusCallback callback) {
        try {
            Call<List<Status>> call = statusService.getStatusByUserId(userId, token);
            call.enqueue(new Callback<List<Status>>() {
                @Override
                public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Status> statuses = response.body();

                        callback.onListStatusFetched(statuses);
                    }
                }

                @Override
                public void onFailure(Call<List<Status>> call, Throwable throwable) {
                    Log.e("Failed to get status", throwable.getMessage());
                }
            });
        }
        catch (Exception e) {
            Log.e("Error get status", e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userAuthentication = SharedPrefManager.getInstance(this).getUser();
        if (userAuthentication != null) {
            String name = userAuthentication.getUsername();
            username.setText(name);
        }
    }
}
