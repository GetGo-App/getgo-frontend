package com.application.getgoproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.application.getgoproject.callback.LocationCommentCallback;
import com.application.getgoproject.models.LocationComment;
import com.application.getgoproject.models.Locations;
import com.application.getgoproject.service.LocationService;
import com.application.getgoproject.utils.RetrofitClient;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListLocationAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Locations> locationsList;
    private String token;

    public ListLocationAdapter(Context context, int layout, List<Locations> locationsList) {
        this.context = context;
        this.layout = layout;
        this.locationsList = locationsList;
    }

    public ListLocationAdapter(Context context, int layout, List<Locations> locationsList, String token) {
        this.context = context;
        this.layout = layout;
        this.locationsList = locationsList;
        this.token = token;
    }

    public void setData(List<Locations> locationsList) {
        this.locationsList = locationsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return locationsList.size();
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

        //ánh xạ view
        TextView name = convertView.findViewById(R.id.tvLocationName);
        TextView price = convertView.findViewById(R.id.tvLocationPrice);
        TextView content = convertView.findViewById(R.id.tvContent);
        ImageView imgLocation = convertView.findViewById(R.id.imageLocation);
        RatingBar locationRating = convertView.findViewById(R.id.locationRating);

        Locations locations = locationsList.get(position);
        name.setText(locations.getName());
        price.setText(locations.getPrice());
        content.setText(locations.getShortDescription());

        getLocationCommentById(locations.getId(), token, new LocationCommentCallback() {
            @Override
            public void onLocationCommentsFetched(List<LocationComment> locationComments) {
                float averageRating = LocationComment.calculateAverageRating(locationComments);
                locationRating.setRating(averageRating);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("Failed to get rating", throwable.getMessage());
            }
        });

        if (locations.getImages() != null && !locations.getImages().isEmpty()) {
            Glide.with(context)
                    .load(locations.getImages().get(0))
                    .into(imgLocation);
        } else {
            imgLocation.setImageResource(R.drawable.sapa);
        }

        return convertView;
    }

    private void getLocationCommentById(int id, String token, LocationCommentCallback callback) {
        try {
            LocationService locationService = RetrofitClient.getRetrofitInstance(context).create(LocationService.class);

            Call<List<LocationComment>> call = locationService.getListLocationCommentById(id, token);
            call.enqueue(new Callback<List<LocationComment>>() {
                @Override
                public void onResponse(Call<List<LocationComment>> call, Response<List<LocationComment>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<LocationComment> locationComments = response.body();

                        callback.onLocationCommentsFetched(locationComments);
                    }
                }

                @Override
                public void onFailure(Call<List<LocationComment>> call, Throwable throwable) {
                    Log.e("Failed to get comment", throwable.getMessage());
                }
            });
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
}
