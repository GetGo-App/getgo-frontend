package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Locations;
import com.bumptech.glide.Glide;

import java.util.List;

public class ListLocationAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Locations> locationsList;

    public ListLocationAdapter(Context context, int layout, List<Locations> locationsList) {
        this.context = context;
        this.layout = layout;
        this.locationsList = locationsList;
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
        TextView tvShowMore = convertView.findViewById(R.id.tvShowMore);

        //gan gia tri
//        Hotel hotel = hotelList.get(position);
//        name.setText(hotel.getNameHotel());
//        price.setText(hotel.getMoney());
//        content.setText(hotel.getCommentHotel());
//        imgLocation.setImageResource(hotel.getImgHotel());

        Locations locations = locationsList.get(position);
        name.setText(locations.getName());
        price.setText(locations.getPrice());
        content.setText(locations.getContent());
        locationRating.setRating(locations.getWebsiteRatingOverall());
        if (locations.getImages() != null && !locations.getImages().isEmpty()) {
            Glide.with(context)
                    .load(locations.getImages().get(0))
                    .into(imgLocation);
        } else {
            imgLocation.setImageResource(R.drawable.sapa);
        }

        tvShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvShowMore.getText().toString().equalsIgnoreCase("Show more")) {
                    content.setMaxLines(Integer.MAX_VALUE);
                    tvShowMore.setText("Show less");
                }
                else {
                    content.setMaxLines(5);
                    tvShowMore.setText("Show more");
                }
            }
        });

        return convertView;
    }
}
