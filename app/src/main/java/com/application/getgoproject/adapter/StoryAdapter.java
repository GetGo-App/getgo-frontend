package com.application.getgoproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.MainActivity;
import com.application.getgoproject.R;
import com.application.getgoproject.StoryActivity;
import com.application.getgoproject.callback.StoryCallback;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.dto.ReactStoryDTO;
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
import java.util.Objects;
import java.util.Random;

import okhttp3.ResponseBody;
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
    private StoryService storyService;

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
        UserAuthentication userAuthentication = SharedPrefManager.getInstance(context).getUser();
        String username = userAuthentication.getUsername();

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
        if (story.getCaption().isEmpty() || story.getCaption().trim().equals("")) {
            holder.captionStory.setVisibility(View.GONE);

        } else holder.captionStory.setText(story.getCaption().trim());

        if (story.getLinkImage() != null) {
            Glide.with(context).load(story.getLinkImage()).into(holder.imgStory);
        }

        holder.numberReaction.setText(String.valueOf(story.getReactCount()));

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof StoryActivity) {
                    ((StoryActivity) context).finish();
                }
            }
        });

        holder.viewers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReactedUsers(story.getId(), token, new StoryCallback() {
                    @Override
                    public void onListUserFetched(List<User> users) {
                        if (users != null && !users.isEmpty()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);

                            LayoutInflater inflater = LayoutInflater.from(context);
                            View dialogView = inflater.inflate(R.layout.dialog_viewers, null);

                            ListView lvViewers = dialogView.findViewById(R.id.lvViewers);

                            List<User> viewerArray = new ArrayList<>(users);
                            ViewerAdapter adapter = new ViewerAdapter(context, R.layout.dialog_layout_viewer, viewerArray);
                            lvViewers.setAdapter(adapter);

                            builder.setView(dialogView);
                            AlertDialog dialog = builder.create();

                            dialog.show();
                        }
                    }
                });
            }
        });
        holder.buttonFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlyingHearts(Objects.requireNonNull(holder).flyContainer);

                getUserByUsername(username, token, new UserCallback() {
                    @Override
                    public void onUserFetched(User user) {
                        if (user != null) {
                            ReactStoryDTO reactStoryDTO = new ReactStoryDTO(user.getId());
                            updateStoryReaction(story.getId(), reactStoryDTO, token);
                        }
                    }
                });
            }
        });

        holder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserByUsername(username, token, new UserCallback() {
                    @Override
                    public void onUserFetched(User user) {
                        if (user != null) {
                            if (user.getId().equals(story.getCreator())) {
                                showPopupMenu(holder.menuButton, story.getId());
                            }
                        }
                    }
                });
            }
        });
    }

    private void addFlyingHearts(FrameLayout container) {
        if (container == null) {
            Log.e("StoryAdapter", "Container is null");
            return;
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (container != null) {

                container.removeAllViews();

                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    final int index = i;
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        if (container == null) return;

                        ImageView heart = new ImageView(context);
                        heart.setImageResource(R.drawable.love);

                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 200);
                        params.leftMargin = random.nextInt(container.getWidth() - 100);
                        params.topMargin = container.getHeight() - 200;
                        heart.setLayoutParams(params);

                        container.addView(heart);

                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.heart_fly);
                        heart.startAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (container != null) {
                                    container.post(() -> container.removeView(heart));
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {}
                        });
                    }, index * 100);
                }
            }
        }, 300);
    }


    @Override
    public int getItemCount() {
        if (storyList == null) {
            return 0;
        }
        return storyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imgStory;
        private ShapeableImageView avatarUser;
        private TextView nameUser, timeCreate, captionStory, numberReaction;
        private ImageButton close, buttonFavor, menuButton, viewers;
        FrameLayout flyContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStory = itemView.findViewById(R.id.imgStory);
            avatarUser = itemView.findViewById(R.id.avatarUser);
            nameUser = itemView.findViewById(R.id.nameUser);
            timeCreate = itemView.findViewById(R.id.timeCreate);
            captionStory = itemView.findViewById(R.id.captionStory);
            close = itemView.findViewById(R.id.close);
            buttonFavor = itemView.findViewById(R.id.buttonFavor);
            flyContainer = itemView.findViewById(R.id.fly_container);
            menuButton = itemView.findViewById(R.id.menuButton);
            numberReaction = itemView.findViewById(R.id.numberReaction);
            viewers = itemView.findViewById(R.id.viewers);
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

    private void getUserByUsername(String username, String token, UserCallback callback) {
        try {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance(context);
            userService = retrofit.create(UserService.class);
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
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStoryReaction(String id, ReactStoryDTO reactStoryDTO, String token) {
        try {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance(context);
            storyService = retrofit.create(StoryService.class);
            Call<ResponseBody> call = storyService.updateStoryReaction(id, reactStoryDTO, token);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d("Update Story", "Update successfully!");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Toast.makeText(context, "Failed to react story!", Toast.LENGTH_LONG).show();
                    throwable.printStackTrace();
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

    private void showPopupMenu(View view, String storyId) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menuEdit) {
                return true;
            }else if (item.getItemId() == R.id.menuDelete) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Story")
                        .setMessage("Are you sure you want to delete this story?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            deleteStoryById(storyId);
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void getReactedUsers(String id, String token, StoryCallback callback) {
        try {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance(context);
            storyService = retrofit.create(StoryService.class);

            Call<List<User>> call = storyService.getReactedUsers(id, token);
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        List<User> userList = response.body();
                        callback.onListUserFetched(userList);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteStoryById(String storyId) {
        try {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance(context);
            storyService = retrofit.create(StoryService.class);
            Call<ResponseBody> call = storyService.deleteStory(storyId, token);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Story deleted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Failed to delete story", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
