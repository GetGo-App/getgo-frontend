package com.application.getgoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.application.getgoproject.models.Image;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context myContext;
    private List<Image> imageList;

    public ImageAdapter(Context myContext, List<Image> imageList) {
        this.myContext = myContext;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
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
            view = LayoutInflater.from(myContext).inflate(R.layout.layout_image_gridview, viewGroup, false);
        }

        Image image = imageList.get(i);
        ImageView imageView = view.findViewById(R.id.imageList);
        imageView.setImageResource(image.getListImage());

        return view;
    }
}
