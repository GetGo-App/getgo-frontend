package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Image;
import com.application.getgoproject.models.ListItem;

import java.util.List;

public class ImageLoctionAdapter extends RecyclerView.Adapter<ImageLoctionAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<Image> imageList;


    public ImageLoctionAdapter(Context context, int layout, List<Image> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageLoctionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_avatar, parent, false);
        return new ImageLoctionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageLoctionAdapter.ViewHolder holder, int position) {
        Image image = imageList.get(position);
        holder.imgLocal.setImageResource(image.getListImage());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        ImageView imgLocal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            imgLocal = itemView.findViewById(R.id.imgAvatar);
        }
    }
}