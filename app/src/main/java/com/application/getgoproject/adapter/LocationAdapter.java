package com.application.getgoproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Locations;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Locations> locationList;
    private int layout;
    private LocationAdapter.OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;

    public LocationAdapter(Context context, int layout, ArrayList<Locations> locationList) {
        this.context = context;
        this.layout = layout;
        this.locationList = locationList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(LocationAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Locations> locationList) {
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_locations, parent, false);
        return new LocationAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locations location = locationList.get(position);
        holder.name.setText(location.getCity());
        if (location.getImages() != null && !location.getImages().isEmpty()) {
            Glide.with(context)
                    .load(location.getImages().get(0))
                    .into(holder.imgLocation);
        } else {
            holder.imgLocation.setImageResource(R.drawable.sapa);
        }

        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
//        holder.tym.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        TextView name;
        ImageView imgLocation;
        ImageButton tym;
        public ViewHolder(@NonNull View itemView, final LocationAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            // Ánh xạ view
            name = itemView.findViewById(R.id.tvNameLocation);
            imgLocation = itemView.findViewById(R.id.imgLocation);
            tym = itemView.findViewById(R.id.btnTym);

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
}