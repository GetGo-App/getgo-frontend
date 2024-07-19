package com.application.getgoproject.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.ChatBox;
import com.application.getgoproject.models.Locations;

import java.util.List;

import io.noties.markwon.Markwon;

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
        if (chatBox.getLocation() != null && !chatBox.getLocation().isEmpty()) {
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
            ((LocationViewHolder) holder).bind(chatBox);
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
        Markwon markwon;

        ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            markwon = Markwon.create(itemView.getContext());
        }

        void bind(ChatBox chatBox) {
            markwon.setMarkdown(textViewMessage, chatBox.getText());
        }
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textsMessage;
        TextView locationMessage;
        RecyclerView listLocation;
        Markwon markwon;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textsMessage = itemView.findViewById(R.id.textMessage);
            locationMessage = itemView.findViewById(R.id.locationMessage);
            listLocation = itemView.findViewById(R.id.lineLocal);
            markwon = Markwon.create(itemView.getContext());
        }

        void bind(ChatBox chatBox) {
            markwon.setMarkdown(textsMessage, chatBox.getText());
            markwon.setMarkdown(locationMessage, chatBox.getLocationMessage());

//            textsMessage.setText(chatBox.getText());
//            locationMessage.setText(chatBox.getLocationMessage());

            InnerLocationAdapter innerLocationAdapter = new InnerLocationAdapter(chatBox.getLocation(), locationClickListener);
            listLocation.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            listLocation.setAdapter(innerLocationAdapter);
        }
    }
}
