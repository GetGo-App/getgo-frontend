package com.application.getgoproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Locations;
import com.bumptech.glide.Glide;

import java.util.List;

public class LocationChatBoxAdapter extends RecyclerView.Adapter<LocationChatBoxAdapter.LocationViewHolder> {

    private List<Locations> locationsList;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Locations location);
    }

    public LocationChatBoxAdapter(List<Locations> locationsList, OnItemClickListener clickListener) {
        this.locationsList = locationsList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chatbox_locations, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Locations location = locationsList.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locationsList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLocation;
        TextView tvNameLocation;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLocation = itemView.findViewById(R.id.imgLocation);
            tvNameLocation = itemView.findViewById(R.id.tvNameLocation);
        }

        void bind(Locations location) {
            tvNameLocation.setText(location.getName());
            Glide.with(itemView.getContext()).load(location.getImages().get(0)).into(imgLocation);
            itemView.setOnClickListener(v -> clickListener.onItemClick(location));
        }
    }
}
