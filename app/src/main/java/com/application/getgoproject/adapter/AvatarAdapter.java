package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Avatar;

import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder> {
    private List<Avatar> avatarList;
    private Context context;
    private int layout;

    private AvatarAdapter.OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;
    public AvatarAdapter(Context context, int layout, List<Avatar> avatarList) {
        this.context = context;
        this.layout = layout;
        this.avatarList = avatarList;
    }

    public void setOnItemClickListener(AvatarAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AvatarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_avatar, parent, false);
        return new AvatarAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarAdapter.ViewHolder holder, int position) {
        Avatar item = avatarList.get(position);
        holder.imgAvatar.setImageResource(item.getImgAvatar());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        ImageView imgAvatar;
        public ViewHolder(@NonNull View itemView, final AvatarAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            // Ánh xạ view
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }
}
