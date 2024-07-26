package com.application.getgoproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.application.getgoproject.adapter.ImageStoryAdapter;
import com.application.getgoproject.adapter.StoryAdapter;
import com.application.getgoproject.models.Story;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.StoryService;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StoryActivity extends AppCompatActivity {

    private UserService userService;
    private StoryService storyService;
    private RecyclerView storyUser;
    private String username, userToken, userId;
    private StoryAdapter storyAdapter;
    private ArrayList<Story> storyArray;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        username = userAuthentication.getUsername();
        userToken = "Bearer " + userAuthentication.getAccessToken();
        userId = getIntent().getStringExtra("creatorId");

        mapping();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);
        storyService = retrofit.create(StoryService.class);

        getUserStories(userId, userToken);

        storyAdapter = new StoryAdapter(this, R.layout.layout_story_user, storyArray, userToken);
        storyUser.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        storyUser.setAdapter(storyAdapter);


    }

    private  void mapping() {
        storyUser = findViewById(R.id.storyUser);
        storyArray = new ArrayList<>();
    }



    private void getUserStories(String userId, String token) {
        try {
            Call<List<Story>> call = storyService.getUserStory(userId, token);
            call.enqueue(new Callback<List<Story>>() {
                @Override
                public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Story> stories = response.body();
                        storyArray.addAll(stories);
                        storyAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Story>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
