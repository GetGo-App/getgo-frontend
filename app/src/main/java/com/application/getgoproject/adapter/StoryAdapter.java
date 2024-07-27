package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.StoryActivity;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.models.Story;
import com.application.getgoproject.models.User;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<Story> storyList;
    private String token;
    private UserService userService;

    public StoryAdapter(Context context, int layout, List<Story> storyList, String token) {
        this.context = context;
        this.layout = layout;
        this.storyList = storyList;
        this.token = token;
    }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new StoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {
        Story story = storyList.get(position);
        getUserByUserId(story.getCreator(), token, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                if (user != null) {
                    holder.nameUser.setText(user.getUserName());
                    if (user.getAvatar() != null) {
                        Glide.with(context).load(user.getAvatar()).into(holder.avatarUser);
                    }
                }
            }
        });

        holder.timeCreate.setText(updateTimeElapsed(story.getCreatedAt().plusHours(7)));
        holder.captionStory.setText(story.getCaption());
        if (story.getLinkImage() != null) {
            Glide.with(context).load(story.getLinkImage()).into(holder.imgStory);
        }


        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof StoryActivity) {
                    ((StoryActivity) context).finish();
                }
            }
        });
        holder.buttonFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (storyList == null) {
            return 0;
        }
        return storyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgStory;
        private ShapeableImageView avatarUser;
        private TextView nameUser, timeCreate, captionStory;
        private ImageButton close, buttonFavor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStory = itemView.findViewById(R.id.imgStory);
            avatarUser = itemView.findViewById(R.id.avatarUser);
            nameUser = itemView.findViewById(R.id.nameUser);
            timeCreate = itemView.findViewById(R.id.timeCreate);
            captionStory = itemView.findViewById(R.id.captionStory);
            close = itemView.findViewById(R.id.close);
            buttonFavor = itemView.findViewById(R.id.buttonFavor);
        }
    }

    private void getUserByUserId(String userId, String token, UserCallback callback) {
        try {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance(context);
            userService = retrofit.create(UserService.class);
            Call<User> call = userService.getUserByUserId(userId, token);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();
                       callback.onUserFetched(user);
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
