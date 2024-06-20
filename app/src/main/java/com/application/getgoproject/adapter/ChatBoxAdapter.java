package com.application.getgoproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.ChatBox;
import com.application.getgoproject.models.Locations;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_LOCATION_RECEIVED = 3;

    private List<ChatBox> messageList;
    private LocationChatBoxAdapter.OnItemClickListener locationClickListener;

    public ChatBoxAdapter(List<ChatBox> messageList, LocationChatBoxAdapter.OnItemClickListener locationClickListener) {
        this.messageList = messageList;
        this.locationClickListener = locationClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        ChatBox chatBox = messageList.get(position);
        if (chatBox.getLocation() != null) {
            return VIEW_TYPE_LOCATION_RECEIVED;
        } else if (chatBox.isSent()) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_message, parent, false);
            return new ReceivedMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chatbox_locations, parent, false);
            return new LocationViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatBox chatBox = messageList.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT) {
            ((SentMessageViewHolder) holder).bind(chatBox);
        } else if (holder.getItemViewType() == VIEW_TYPE_MESSAGE_RECEIVED) {
            ((ReceivedMessageViewHolder) holder).bind(chatBox);
        } else {
            ((LocationViewHolder) holder).bind(chatBox.getLocation(), locationClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof LocationViewHolder) {
            ((LocationViewHolder) holder).onViewRecycled();
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof LocationViewHolder) {
            ((LocationViewHolder) holder).onViewAttachedToWindow();
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof LocationViewHolder) {
            ((LocationViewHolder) holder).onViewDetachedFromWindow();
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;

        SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }

        void bind(ChatBox chatBox) {
            textViewMessage.setText(chatBox.getText());
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;

        ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }

        void bind(ChatBox chatBox) {
            textViewMessage.setText(chatBox.getText());
        }
    }

    class LocationViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        ImageView imgLocation;
        TextView tvNameLocation;
        MapView mapView;
        GoogleMap googleMap;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLocation = itemView.findViewById(R.id.imgLocation);
            tvNameLocation = itemView.findViewById(R.id.tvNameLocation);
            mapView = itemView.findViewById(R.id.map_view);
            // Initialize the MapView
            mapView.onCreate(null);
            mapView.onResume(); // Needed to get the map to display immediately
            mapView.getMapAsync(this);
        }

        void bind(Locations location, LocationChatBoxAdapter.OnItemClickListener locationClickListener) {
            tvNameLocation.setText(location.getName());
            if (location.getImages() != null && !location.getImages().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(location.getImages().get(0))
                        .into(imgLocation);
            } else {
                imgLocation.setImageResource(R.drawable.sapa);
            }

            itemView.setOnClickListener(v -> locationClickListener.onItemClick(location));

            if (googleMap != null) {
                updateMap(location);
            } else {
                mapView.getMapAsync(googleMap -> {
                    this.googleMap = googleMap;
                    updateMap(location);
                });
            }
        }

        void updateMap(Locations location) {
            LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(locationLatLng).title(location.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15));
        }

        void onViewRecycled() {
            if (mapView != null) {
                mapView.onPause();
                mapView.onDestroy();
            }
        }

        void onViewAttachedToWindow() {
            if (mapView != null) {
                mapView.onResume();
            }
        }

        void onViewDetachedFromWindow() {
            if (mapView != null) {
                mapView.onPause();
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            this.googleMap = googleMap;
        }
    }
}
