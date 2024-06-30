package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.StatusAdapter;
import com.application.getgoproject.callback.StatusCallback;
import com.application.getgoproject.callback.UserCallback;
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

public class ListStatusActivity extends AppCompatActivity {
    private StatusService statusService;
    private UserService userService;
    private String userToken;

    private ImageButton imgbtnGoback;
    private ShapeableImageView avatar;
    private FrameLayout statusLayout;
    private List<Status> statusList;
    private Button btnAddStatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_status);

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        String username = userAuthentication.getUsername();
        userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        statusService = retrofit.create(StatusService.class);
        userService = retrofit.create(UserService.class);

        mapping();

        getUserByUsername(username, userToken, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                if (!user.getAvatar().isEmpty()) {
                    Glide.with(ListStatusActivity.this)
                            .load(user.getAvatar())
                            .into(avatar);
                }
                else {
                    avatar.setImageResource(R.drawable.avatar);
                }
            }
        });

        addLayoutStatus();
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        btnAddStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newStatusForm();
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
        btnAddStatus = findViewById(R.id.btnAddStatus);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        statusLayout = findViewById(R.id.statusLayout);
    }
    private void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void newStatusForm() {
        Intent intent = new Intent(this, NewStatusActivity.class);
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

        StatusAdapter statusAdapter = new StatusAdapter(this, statusList, userToken);

        getAllStatus(userToken, new StatusCallback() {
            @Override
            public void onListStatusFetched(List<Status> statuses) {
                statusList.clear();
                for (Status status : statuses) {
                    statusList.add(new Status(status.getUploader(), status.getContent(),
                            status.getUploadedTime(), status.getPrivacyMode(),
                            status.getImages(), status.getReactedUsers()));
                }
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

    private void getAllStatus(String token, StatusCallback callback) {
        try {
            Call<List<Status>> call = statusService.getAllStatus(token);
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

    public void getUserByUsername(String username, String token, UserCallback callback) {
        try {
            Call<User> call = userService.getUserByUsername(username, token);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();
                        callback.onUserFetched(user);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    // Handle failure
                }
            });
        }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
