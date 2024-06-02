package com.application.getgoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.getgoproject.models.Message;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<Message> userMessageList;

    public MessageAdapter(Context context, List<Message> userMessageList) {
        this.context = context;
        this.userMessageList = userMessageList;
    }

    @Override
    public int getCount() {
        return userMessageList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_message_listview, viewGroup, false);
        }

        Message userMessage = userMessageList.get(i);

        ImageView userAvatar = view.findViewById(R.id.userAvatar);
        TextView username = view.findViewById(R.id.username);
        TextView userLastMessage = view.findViewById(R.id.userLastMessage);
        TextView lastTime = view.findViewById(R.id.lastTimeMessage);

        userAvatar.setImageResource(userMessage.getUserAvatar());
        username.setText(userMessage.getUsername());
        userLastMessage.setText(userMessage.getUserMessage());
        lastTime.setText(userMessage.getLastTime());

        return view;
    }
}
