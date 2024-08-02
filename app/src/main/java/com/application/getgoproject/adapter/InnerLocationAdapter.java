package com.application.getgoproject.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Locations;

import java.util.List;

public class InnerLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_START_LOCATION = 1;
    private static final int VIEW_TYPE_LINE_LOCATION = 2;

    private List<Locations> locationList;
    private LocationChatBoxAdapter.OnItemClickListener locationClickListener;

    public InnerLocationAdapter(List<Locations> locationList, LocationChatBoxAdapter.OnItemClickListener locationClickListener) {
        this.locationList = locationList;
        this.locationClickListener = locationClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_START_LOCATION) {
            View view = inflater.inflate(R.layout.item_start_location, parent, false);
            return new StartLocationViewHolder(view);
        } else if (viewType == VIEW_TYPE_LINE_LOCATION) {
            View view = inflater.inflate(R.layout.item_line_location, parent, false);
            return new LineLocationViewHolder(view);
        }

        throw new IllegalArgumentException("Invalid view type in InnerLocationAdapter");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_START_LOCATION) {
            ((StartLocationViewHolder) holder).bind();
        } else if (holder.getItemViewType() == VIEW_TYPE_LINE_LOCATION) {
            Locations location = locationList.get(position - 1);
            ((LineLocationViewHolder) holder).bind(location, locationClickListener);
        }
    }

    @Override
    public int getItemCount() {
        // Add 1 for the header
        return locationList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_START_LOCATION;
        } else {
            return VIEW_TYPE_LINE_LOCATION;
        }
    }

    public class StartLocationViewHolder extends RecyclerView.ViewHolder {
        // Define your views for item_start_location
        TextView textViewStartLocation;

        public StartLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStartLocation = itemView.findViewById(R.id.tvStart);
        }

        public void bind() {
            // Bind data for item_start_location
            textViewStartLocation.setText("You'll start at");
        }
    }

    public class LineLocationViewHolder extends RecyclerView.ViewHolder {
        // Define your views for item_line_location
        TextView titleChatLocation;
        TextView textDetails;
        TextView textCount;

        public LineLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleChatLocation = itemView.findViewById(R.id.titleChatLocation);
            textDetails = itemView.findViewById(R.id.textDetails);
            textCount = itemView.findViewById(R.id.textCount);

            textDetails.setPaintFlags(textDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        public void bind(Locations location, LocationChatBoxAdapter.OnItemClickListener locationClickListener) {
            // Bind data for item_line_location
            titleChatLocation.setText(location.getName());
            textCount.setText(String.valueOf(getAdapterPosition()));

            itemView.setOnClickListener(v -> locationClickListener.onItemClick(location));
        }
    }
}
