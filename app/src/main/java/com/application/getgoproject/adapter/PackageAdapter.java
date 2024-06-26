package com.application.getgoproject.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.ListPackage;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<ListPackage> packageList;

    private OnItemClickListener onItemClickListener;
    private int selectedPosition = 0;
    public PackageAdapter(Context context, int layout, List<ListPackage> packageList) {
        this.context = context;
        this.layout = layout;
        this.packageList = packageList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_package_time, parent, false);
        return new PackageAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.ViewHolder holder, int position) {
        ListPackage packages = packageList.get(position);
        holder.tvTitlePackage.setText(packages.getTitle());
        holder.tvDetailPackage.setText(packages.getPrice());

        //đổi màu background
        if (position == selectedPosition) {
            holder.itemView.setBackground(context.getDrawable(R.drawable.background_status_item));
        } else {
            holder.itemView.setBackground(context.getDrawable(R.drawable.border_button));
        }
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        TextView tvTitlePackage, tvDetailPackage;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            // Ánh xạ view
            tvTitlePackage = itemView.findViewById(R.id.tvTitlePackage);
            tvDetailPackage = itemView.findViewById(R.id.tvDetailPackage);

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