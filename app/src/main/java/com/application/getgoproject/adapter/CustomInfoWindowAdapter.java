package com.application.getgoproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomInfoWindowAdapter";
    private final View mWindow;
    private final Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        String imageUrl = (String) marker.getTag();

        TextView titleView = view.findViewById(R.id.title);
        ImageView imageView = view.findViewById(R.id.image);

        if (title != null) {
            titleView.setText(title);
            imageView.setContentDescription(title);
        } else {
            imageView.setContentDescription(mContext.getString(R.string.image_desc));
        }

        // Log the image URL
        Log.d(TAG, "Image URL: " + imageUrl);

        // Load image using Glide
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(mContext)
                    .load(imageUrl)
                    .override(300, 200)
                    .into(imageView);
        } else {
            Log.e(TAG, "Image URL is null or empty");
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
