package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class ImageStatusAdapter extends RecyclerView.Adapter<ImageStatusAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> imageResources;

    public ImageStatusAdapter(Context context, ArrayList<String> imageResources) {
        this.context = context;
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgRes = imageResources.get(position);
        if (!imgRes.isEmpty()) {
            Glide.with(context)
                    .load(imgRes)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(holder.imageViewStatus);
        }
    }

    @Override
    public int getItemCount() {
        return imageResources.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewStatus = itemView.findViewById(R.id.imageStatus);
        }
    }
}
