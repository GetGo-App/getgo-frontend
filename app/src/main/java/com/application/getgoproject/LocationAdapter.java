package com.application.getgoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Locations> locationList;
    private int layout;

    public LocationAdapter(Context context, int layout, ArrayList<Locations> locationList) {
        this.context = context;
        this.layout = layout;
        this.locationList = locationList;
    }

    public void setData(ArrayList<Locations> locationList) {
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_locations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locations location = locationList.get(position);
        holder.name.setText(location.getName());
        holder.imgLocation.setImageResource(location.getImage());
        holder.tym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            name = (TextView) itemView.findViewById(R.id.tvNameLocation);
            imgLocation = (ImageView) itemView.findViewById(R.id.imgLocation);
            tym = (ImageButton) itemView.findViewById(R.id.btnTym);
        }
    }
}