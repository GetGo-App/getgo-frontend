package com.application.getgoproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.util.TypedValue;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.LocationAdapter;
import com.application.getgoproject.models.Locations;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {
    private TextView tvAll, tvTrending, tvTopyear, tvFavorite;
    private SearchView etSearchLocation;
    private RecyclerView recycler;
    private LocationAdapter locationAdapter;
    private ArrayList<Locations> arrayLocation, arrayTopyear, arrayTrending, arrayFavor;
    private ImageButton imgbtnGoback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_locations);
        anhXa();

        recycler.setLayoutManager(new GridLayoutManager(this, 2));

        int spacingInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        recycler.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        locationAdapter = new LocationAdapter(this, R.layout.layout_locations, arrayLocation);
        recycler.setAdapter(locationAdapter);

        locationAdapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                locationAdapter.setSelectedPosition(position);

                Intent intent = new Intent(LocationActivity.this, ListLocationlActivity.class);
                intent.putExtra("location", arrayLocation.get(position).getCity());
                startActivity(intent);
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });

        updateTextViewStyles(tvAll, tvTrending, tvTopyear, tvFavorite);

        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvAll, tvTrending, tvTopyear, tvFavorite);
                locationAdapter.setData(arrayLocation);
            }
        });

        tvTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvTrending, tvTopyear, tvAll, tvFavorite);
                locationAdapter.setData(arrayTrending);
            }
        });

        tvTopyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvTopyear, tvAll, tvTrending, tvFavorite);
                locationAdapter.setData(arrayTopyear);
            }
        });

        tvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvFavorite, tvAll, tvTrending, tvTopyear);
                locationAdapter.setData(arrayFavor);
            }
        });
        etSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void anhXa(){
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        etSearchLocation = findViewById(R.id.etSearchLocation);
        tvAll = findViewById(R.id.tvAll);
        tvTrending = findViewById(R.id.tvTrending);
        tvTopyear = findViewById(R.id.tvTopyear);
        tvFavorite = findViewById(R.id.tvFavorite);
        recycler = findViewById(R.id.recyclerView);

        arrayLocation = new ArrayList<>();
        arrayLocation.add(new Locations("Quận 1", "", "", "HCM", 0,5, R.drawable.sapa, true,"","","","","",5,true, true));
        arrayLocation.add(new Locations("Quận 2", "", "", "HCM", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Quận 3", "", "", "HCM", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Hoàng Mai", "", "Hà Nội", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Đống Đa", "", "", "Hà Nội", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Thanh Yên", "", "", "Hà Nội", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Đăk Lăk", "", "", "Đà Nẵng", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Gia Lai", "", "", "Đà Nẵng", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Tp Hồ Chí Minh", "", "Đà Nẵng", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Hà Nội", "", "", "Đà Nẵng", 0,5, R.drawable.sapa, true,"","","","","",5,true, true));

        arrayTopyear = new ArrayList<>();
        arrayTrending = new ArrayList<>();
        arrayFavor = new ArrayList<>();
        for (int i=0; i< arrayLocation.size(); i++ ){
            if (arrayLocation.get(i).isTrend()) {
                arrayTrending.add(arrayLocation.get(i));
            }
            if (arrayLocation.get(i).isTopyear()) {
                arrayTopyear.add(arrayLocation.get(i));
            }
        }
    }

    private void homeForm(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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