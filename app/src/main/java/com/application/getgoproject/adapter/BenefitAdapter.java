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

public class BenefitAdapter extends RecyclerView.Adapter<BenefitAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<ListItem> itemList;


    public BenefitAdapter(Context context, int layout, List<ListItem> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BenefitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_benefit, parent, false);
        return new BenefitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BenefitAdapter.ViewHolder holder, int position) {
        ListItem item = itemList.get(position);
        holder.tvTitleIcon.setText(item.getTitle());
        holder.tvDetailIcon.setText(item.getDetail());
        holder.imgIcon.setImageResource(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        TextView tvTitleIcon, tvDetailIcon;
        ImageView imgIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            tvTitleIcon = itemView.findViewById(R.id.tvTitleIcon);
            tvDetailIcon = itemView.findViewById(R.id.tvDetailIcon);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
