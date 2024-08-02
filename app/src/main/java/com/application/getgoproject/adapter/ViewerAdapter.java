package com.application.getgoproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.User;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ViewerAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final List<User> arrayViewers;

    public ViewerAdapter(Context context, int layout, List<User> arrayViewers) {
        this.context = context;
        this.layout = layout;
        this.arrayViewers = arrayViewers;
    }

    @Override
    public int getCount() {
        if (arrayViewers != null) {
            return arrayViewers.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, parent, false);
        }

        ShapeableImageView avatarViewer = (ShapeableImageView) convertView.findViewById(R.id.avatarViewer);
        TextView nameViewer = (TextView) convertView.findViewById(R.id.nameViewer);

        User user = arrayViewers.get(position);
        nameViewer.setText(user.getUserName());
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            Glide.with(context)
                    .load(user.getAvatar())
                    .into(avatarViewer);
        }
        else {
            avatarViewer.setImageResource(R.drawable.avatar);
        }

        return convertView;
    }
}
