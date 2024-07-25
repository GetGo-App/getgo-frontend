package com.application.getgoproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageStoryAdapter extends RecyclerView.Adapter<ImageStoryAdapter.ImageHolder> {
    private final List<String> imgList;

    public ImageStoryAdapter(List<String> imgList) {
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_story, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        String imgStory = imgList.get(position);

        if (imgStory != null) {
            if (!imgStory.isEmpty()) {
                Glide.with(holder.itemView.getContext())
                        .load(imgStory)
                        .into(holder.imgItemStory);
            }
            else {
                holder.imgItemStory.setImageResource(R.drawable.loading);
            }
        }
        else {
            holder.imgItemStory.setImageResource(R.drawable.loading);
        }
    }

    @Override
    public int getItemCount() {
        if (imgList != null) {
            return imgList.size();
        }
        return 0;
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        private final ImageView imgItemStory;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            imgItemStory = itemView.findViewById(R.id.imgItemStory);
        }
    }
}

