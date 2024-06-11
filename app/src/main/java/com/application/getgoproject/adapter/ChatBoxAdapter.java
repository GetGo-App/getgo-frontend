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
            ((LocationViewHolder) holder).bind(chatBox.getLocation());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
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
            if (location.getImages() != null && !location.getImages().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(location.getImages().get(0))
                        .into(imgLocation);
            } else {
                imgLocation.setImageResource(R.drawable.sapa);
            }
            itemView.setOnClickListener(v -> locationClickListener.onItemClick(location));
        }
    }
}
