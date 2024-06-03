package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.ListItem;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<ListItem> itemList;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;
    public ListItemAdapter(Context context, int layout, List<ListItem> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_profile, parent, false);
        return new ListItemAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.ViewHolder holder, int position) {
        ListItem item = itemList.get(position);
        holder.tvItem.setText(item.getTitle());
        holder.imgIcon.setImageResource(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        TextView tvItem;
        ImageView imgIcon;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            // Ánh xạ view
            tvItem = itemView.findViewById(R.id.tvItem);
            imgIcon = itemView.findViewById(R.id.imgIcon);
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
