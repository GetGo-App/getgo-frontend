package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.adapter.HotelAdapter;
import com.application.getgoproject.models.Hotel;

import java.util.ArrayList;

public class ListLocationlActivity extends AppCompatActivity {
    private ListView lvHotel;
    private ArrayList<Hotel> arrayHotel;
    private HotelAdapter adapter;
    private ImageButton imgbtnGoback;
    private TextView tvNamePlace;
    private TextView tvAll, tvTrending, tvTopyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_locations);

        mapping();
        Intent intent = getIntent();
        String namePlace = intent.getStringExtra("location");
        tvNamePlace.setText(namePlace);

        adapter = new HotelAdapter(this, R.layout.layout_item_locations, arrayHotel);
        lvHotel.setAdapter(adapter);

        lvHotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListLocationlActivity.this, DetailLocationActivity.class);
                intent.putExtra("detail location",arrayHotel.get(position).getNameHotel());
                startActivity(intent);
            }
        });
        updateTextViewStyles(tvAll, tvTrending, tvTopyear);
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvAll, tvTrending, tvTopyear);
            }
        });

        tvTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvTrending, tvTopyear, tvAll);
            }
        });

        tvTopyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvTopyear, tvAll, tvTrending);
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationForm();
            }
        });

    }
    private void mapping(){
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        lvHotel = (ListView) findViewById(R.id.lvHotel);
        tvAll = findViewById(R.id.tvAll);
        tvTrending = findViewById(R.id.tvTrending);
        tvTopyear = findViewById(R.id.tvTopyear);
        tvNamePlace = findViewById(R.id.tvNamePlace);
        arrayHotel = new ArrayList<>();
        arrayHotel.add(new Hotel(R.drawable.border_gradient, "Pao's Sapa Leisure Hote", "Located in Sa Pa, 6.1 km from Fansipan Legend Cable Car Station, Pao's Sapa Leisure Hotel provides accommodation with a fitness centre, free private parking, a garden and a shared lounge. Among the facilities of this property are a restaurant, a kids' club and room service, along with free WiFi. The accommodation offers a 24-hour front desk, a concierge service and currency exchange for guests.", "VND 1,872,000"));
        arrayHotel.add(new Hotel(R.drawable.border_gradient, "Sapa Eco Villas & Spa", "With city views, Sapa Eco Villas & Spa is situated in Sa Pa and has a restaurant, room service, bar, garden, year-round outdoor pool and terrace. Complimentary WiFi is available throughout the property.", "VND 1,330,000"));
        arrayHotel.add(new Hotel(R.drawable.border_gradient, "HOTEL DE SAPA", "HOTEL DE SAPA in Sa Pa features 4-star accommodation with a terrace and a bar. Offering a restaurant, the property also has a garden, as well as an indoor pool and a sauna. The accommodation offers room service, a 24-hour front desk and currency exchange for guests.", "VND 936,000"));
        arrayHotel.add(new Hotel(R.drawable.border_gradient, "Pao's Sapa Leisure Hote", "Located in Sa Pa, 6.1 km from Fansipan Legend Cable Car Station, Pao's Sapa Leisure Hotel provides accommodation with a fitness centre, free private parking, a garden and a shared lounge. Among the facilities of this property are a restaurant, a kids' club and room service, along with free WiFi. The accommodation offers a 24-hour front desk, a concierge service and currency exchange for guests.", "VND 1,872,000"));
        arrayHotel.add(new Hotel(R.drawable.border_gradient, "Sapa Eco Villas & Spa", "With city views, Sapa Eco Villas & Spa is situated in Sa Pa and has a restaurant, room service, bar, garden, year-round outdoor pool and terrace. Complimentary WiFi is available throughout the property.", "VND 1,330,000"));
        arrayHotel.add(new Hotel(R.drawable.border_gradient, "HOTEL DE SAPA", "HOTEL DE SAPA in Sa Pa features 4-star accommodation with a terrace and a bar. Offering a restaurant, the property also has a garden, as well as an indoor pool and a sauna. The accommodation offers room service, a 24-hour front desk and currency exchange for guests.", "VND 936,000"));
    }

    private void locationForm(){
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    private void updateTextViewStyles(TextView selectedTextView, TextView... otherTextViews) {
        selectedTextView.setBackgroundResource(R.drawable.underline_button);
        selectedTextView.setTextColor(getColor(R.color.black));

        for (TextView textView : otherTextViews) {
            textView.setBackgroundResource(R.drawable.not_underline_button);
            textView.setTextColor(Color.parseColor("#858585"));
        }
    }
}
