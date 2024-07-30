package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.User;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ViewerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<User> arrayViewers;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        ShapeableImageView avatarViewer = (ShapeableImageView) convertView.findViewById(R.id.avatarViewer);
        TextView nameViewer = (TextView) convertView.findViewById(R.id.nameViewer);

        User user = arrayViewers.get(position);
//        avatarViewer.setImageResource(R.drawable.avatar);
//        nameViewer.setText("thu huong");
        return convertView;
    }
}
