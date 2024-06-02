package com.application.getgoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageStatusAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Integer> imageResources;

    public ImageStatusAdapter(Context context, ArrayList<Integer> imageResources) {
        this.context = context;
        this.imageResources = imageResources;
    }

    @Override
    public int getCount() {
        return imageResources.size();
    }

    @Override
    public Object getItem(int i) {
        return imageResources.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_image_status, viewGroup, false);
        }

        ImageView imageViewStatus = view.findViewById(R.id.imageStatus);
        imageViewStatus.setImageResource(imageResources.get(i));

        return view;
    }
}
