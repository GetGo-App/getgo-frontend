package com.application.getgoproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.StoryActivity;
import com.application.getgoproject.models.Story;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class StoryInHomeAdapter extends RecyclerView.Adapter<StoryInHomeAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<Story> storyArray;
    private AvatarAdapter.OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;

    public StoryInHomeAdapter(Context context, int layout, List<Story> storyArray) {
        this.context = context;
        this.layout = layout;
        this.storyArray = storyArray;
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
        holder.imgAvatar.setImageResource(story.getAvatar());
        holder.userStoryName.setText(story.getUsername());

        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
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
}
