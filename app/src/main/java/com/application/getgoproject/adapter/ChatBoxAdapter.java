package com.application.getgoproject.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private static final int VIEW_TYPE_TYPING_INDICATOR = 4;

    private List<ChatBox> messageList;
    private LocationChatBoxAdapter.OnItemClickListener locationClickListener;

    public ChatBoxAdapter(List<ChatBox> messageList, LocationChatBoxAdapter.OnItemClickListener locationClickListener) {
        this.messageList = messageList;
        this.locationClickListener = locationClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        ChatBox chatBox = messageList.get(position);
        if (chatBox.isTypingIndicator()) {
            return VIEW_TYPE_TYPING_INDICATOR;
        } else if (chatBox.getLocation() != null && !chatBox.getLocation().isEmpty()) {
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
        } else if (viewType == VIEW_TYPE_LOCATION_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chatbox_locations, parent, false);
            return new LocationViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typing_indicator, parent, false);
            return new TypingIndicatorViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatBox chatBox = messageList.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT) {
            ((SentMessageViewHolder) holder).bind(chatBox);
        } else if (holder.getItemViewType() == VIEW_TYPE_MESSAGE_RECEIVED) {
            ((ReceivedMessageViewHolder) holder).bind(chatBox);
        } else if (holder.getItemViewType() == VIEW_TYPE_LOCATION_RECEIVED) {
            ((LocationViewHolder) holder).bind(chatBox);
        } else if (holder.getItemViewType() == VIEW_TYPE_TYPING_INDICATOR) {
            ((TypingIndicatorViewHolder) holder).bind();
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

            InnerLocationAdapter innerLocationAdapter = new InnerLocationAdapter(chatBox.getLocation(), locationClickListener);
            listLocation.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            listLocation.setAdapter(innerLocationAdapter);
        }
    }

    static class TypingIndicatorViewHolder extends RecyclerView.ViewHolder {
        TextView dot1;
        TextView dot2;
        TextView dot3;

        TypingIndicatorViewHolder(@NonNull View itemView) {
            super(itemView);
            dot1 = itemView.findViewById(R.id.dot1);
            dot2 = itemView.findViewById(R.id.dot2);
            dot3 = itemView.findViewById(R.id.dot3);
        }

        void bind() {
            dot1.setVisibility(View.VISIBLE);
            dot2.setVisibility(View.VISIBLE);
            dot3.setVisibility(View.VISIBLE);

            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.typing_indicator_anim);
            dot1.startAnimation(animation);
            dot2.startAnimation(animation);
            dot3.startAnimation(animation);
        }
    }
}
