package com.application.getgoproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.application.getgoproject.adapter.ImageStoryAdapter;
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

    private String username, userToken, userId;
    private ViewPager2 vpImageStory;
    private CircleIndicator3 circleIndicator;
    private ShapeableImageView avatarUser;
    private TextView nameUser, timeCreate;
    private ImageButton close;
    private List<String> arrayImage;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = vpImageStory.getCurrentItem();
            if (currentPosition == arrayImage.size() - 1) {
                vpImageStory.setCurrentItem(0);
            } else {
                vpImageStory.setCurrentItem(currentPosition +1 );
            }
        }
    };

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

        getUserByUserId(userId, userToken);
        getUserStories(userId, userToken);

        vpImageStory.setOffscreenPageLimit(3);
        vpImageStory.setClipToPadding(false);
        vpImageStory.setClipChildren(false);

        arrayImage = new ArrayList<>();

        vpImageStory.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });


        ImageStoryAdapter adapter = new ImageStoryAdapter(arrayImage);
        vpImageStory.setAdapter(adapter);
        circleIndicator.setViewPager(vpImageStory);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  void mapping() {
        vpImageStory = findViewById(R.id.vpImageStory);
        circleIndicator = findViewById(R.id.circleIndicator);
        avatarUser = findViewById(R.id.avatarUser);
        nameUser = findViewById(R.id.nameUser);
        close = findViewById(R.id.close);
        timeCreate = findViewById(R.id.timeCreate);
    }

    private void getUserByUserId(String userId, String token) {
        try {
            Call<User> call = userService.getUserByUserId(userId, token);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();
                        nameUser.setText(user.getUserName());
                        Glide.with(getApplicationContext())
                                .load(user.getAvatar())
                                .placeholder(R.drawable.avatar)
                                .error(R.drawable.avatar)
                                .into(avatarUser);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserStories(String userId, String token) {
        try {
            Call<List<Story>> call = storyService.getUserStory(userId, token);
            call.enqueue(new Callback<List<Story>>() {
                @Override
                public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Story> stories = response.body();
                        for (Story story : stories) {
                            arrayImage.add(story.getLinkImage());
                        }

                        timeCreate.setText(updateTimeElapsed(stories.get(0).getCreatedAt().plusHours(7)));

                        ImageStoryAdapter adapter = new ImageStoryAdapter(arrayImage);
                        vpImageStory.setAdapter(adapter);
                        circleIndicator.setViewPager(vpImageStory);
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

    private String updateTimeElapsed(LocalDateTime createdAt) {
        try {
            Duration duration = Duration.between(createdAt, LocalDateTime.now());

            long seconds = duration.getSeconds();
            String timeAgo;

            if (seconds < 60) {
                timeAgo = seconds + "s";
            } else if (seconds < 3600) {
                timeAgo = seconds / 60 + "m";
            } else if (seconds < 86400) {
                timeAgo = seconds / 3600 + "h";
            } else {
                timeAgo = seconds / 86400 + "d";
            }

            return timeAgo;
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

}
