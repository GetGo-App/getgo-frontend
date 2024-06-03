package com.application.getgoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageStatusAdapter extends RecyclerView.Adapter<ImageStatusAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Integer> imageResources;

    public ImageStatusAdapter(Context context, ArrayList<Integer> imageResources) {
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
        int imgRes = imageResources.get(position);
        holder.imageViewStatus.setImageResource(imgRes);
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
