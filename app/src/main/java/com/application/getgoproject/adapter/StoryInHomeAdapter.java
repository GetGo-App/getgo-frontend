package com.application.getgoproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryInHomeAdapter extends RecyclerView.Adapter<StoryInHomeAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<Story> storyArray;
    private AvatarAdapter.OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;
    private String token;

    public StoryInHomeAdapter(Context context, int layout, List<Story> storyArray, String token) {
        this.context = context;
        this.layout = layout;
        this.storyArray = storyArray;
        this.token = token;
    }

    @NonNull
    @Override
    public StoryInHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new StoryInHomeAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryInHomeAdapter.ViewHolder holder, int position) {
        Story story = storyArray.get(position);

        if (story.getLinkImage() != null) {
            if (!story.getLinkImage().isEmpty()) {
                Glide.with(context)
                        .load(story.getLinkImage())
                        .into(holder.imgAvatar);
            }
            else {
                holder.imgAvatar.setImageResource(R.drawable.avatar);
            }
        }
        else {
            holder.imgAvatar.setImageResource(R.drawable.avatar);
        }

        getUserByUserId(story.getCreator(), token, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                if (user != null) {
                    holder.userStoryName.setText(user.getUserName());
                }
            }
        });

        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("creatorId", story.getCreator());
                context.startActivity(intent);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(AvatarAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        if (storyArray == null) {
            return 0;
        }
        return storyArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imgAvatar;
        private TextView userStoryName;
        public ViewHolder(@NonNull View itemView, final AvatarAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            imgAvatar = (ShapeableImageView) itemView.findViewById(R.id.imgAvatar);
            userStoryName = (TextView) itemView.findViewById(R.id.userStoryName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
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
