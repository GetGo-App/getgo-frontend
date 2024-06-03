package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Hotel;

import java.util.List;

public class HotelAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Hotel> hotelList;

    public HotelAdapter(Context context, int layout, List<Hotel> hotelList) {
        this.context = context;
        this.layout = layout;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
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
        TextView name = convertView.findViewById(R.id.tvNamehotel);
        TextView money = convertView.findViewById(R.id.tvMoneyHotel);
        TextView comment = convertView.findViewById(R.id.tvDetailHotel);
        ImageView imgHotel = convertView.findViewById(R.id.imgHotel);

        //gan gia tri
        Hotel hotel = hotelList.get(position);
        name.setText(hotel.getNameHotel());
        money.setText(hotel.getMoney());
        comment.setText(hotel.getCommentHotel());
        imgHotel.setImageResource(hotel.getImgHotel());

        return convertView;
    }
}
