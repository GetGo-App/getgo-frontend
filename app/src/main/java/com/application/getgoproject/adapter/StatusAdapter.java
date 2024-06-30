package com.application.getgoproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.application.getgoproject.R;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.models.Status;
import com.application.getgoproject.models.User;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private Context context;
    private List<Status> statusList;
    private String token;

    public StatusAdapter(Context context, List<Status> statusList, String token) {
        this.context = context;
        this.statusList = statusList;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_status_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Status status = statusList.get(position);

        getUserByUserId(status.getUploader(), token, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                holder.username.setText(user.getUserName());

                if (!user.getAvatar().isEmpty()) {
                    Glide.with(context)
                            .load(user.getAvatar())
                            .into(holder.avatar);
                }
                else {
                    holder.avatar.setImageResource(R.drawable.avatar);
                }
            }
        });

        holder.lastTime.setText(getTimeAgo(status.getUploadedTime()));
        holder.content.setText(status.getContent());

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        if (status.getImages().size() == 1) {
            staggeredGridLayoutManager.setSpanCount(1);
        }
        else if (status.getImages().size() >= 2) {
            staggeredGridLayoutManager.setSpanCount(2);
        }

        holder.listImage.setLayoutManager(staggeredGridLayoutManager);

        ImageStatusAdapter adapter = new ImageStatusAdapter(context, status.getImages());

        holder.listImage.setAdapter(adapter);
        holder.reactedUsers.setText((status.getReactedUsers() != null ? status.getReactedUsers().size() : 0) + " reactions");

    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public String getTimeAgo(LocalDateTime createdDate) {
        LocalDateTime adjustDate = createdDate.plusHours(7);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(adjustDate, now);

        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return "just now";
        } else if (seconds < 3600) {
            long minutes = TimeUnit.SECONDS.toMinutes(seconds);
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        } else if (seconds < 86400) {
            long hours = TimeUnit.SECONDS.toHours(seconds);
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        } else if (seconds < 2592000) {
            long days = TimeUnit.SECONDS.toDays(seconds);
            return days + (days == 1 ? " day ago" : " days ago");
        } else if (seconds < 31104000) {
            long months = TimeUnit.SECONDS.toDays(seconds) / 30;
            return months + (months == 1 ? " month ago" : " months ago");
        } else {
            long years = TimeUnit.SECONDS.toDays(seconds) / 365;
            return years + (years == 1 ? " year ago" : " years ago");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView avatar;
        TextView username;
        TextView lastTime;
        TextView content;
        RecyclerView listImage;
        ImageButton heartButton, btnMenu;
        TextView reactedUsers;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            username = itemView.findViewById(R.id.username);
            lastTime = itemView.findViewById(R.id.userLastUploadTime);
            content = itemView.findViewById(R.id.statusContent);
            listImage = itemView.findViewById(R.id.recyclerViewImage);
            heartButton = itemView.findViewById(R.id.heartButton);
            btnMenu = itemView.findViewById(R.id.btnMenu);
            reactedUsers = itemView.findViewById(R.id.reactedUsers);

            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showMenu();
                }
            });
        }

//        private void showMenu() {
//            PopupMenu menu = new PopupMenu(itemView, btnMenu);
//            menu.getMenuInflater().inflate(R.menu.menu_popup, menu.getMenu());
//            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    switch ((item.getItemId())){
//                        case (R.id.menuEdit):
//                            break;
//                        case (R.id.menuDelete):
//                            break;;
//                    }
//                    return false;
//                }
//            });
//            menu.show();
//        }
    }

    public void getUserByUserId(String id, String token, UserCallback callback) {
        try {
            UserService userService = RetrofitClient.getRetrofitInstance(context).create(UserService.class);

            Call<User> call = userService.getUserByUserId(id, token);
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

                }
            });
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
}
