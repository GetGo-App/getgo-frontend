package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.util.TypedValue;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

        int spacingInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        recycler.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        locationAdapter = new LocationAdapter(this, R.layout.layout_locations, arrayLocation);
        recycler.setAdapter(locationAdapter);

        locationAdapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                locationAdapter.setSelectedPosition(position);

                Intent intent = new Intent(LocationActivity.this, ListLocationlActivity.class);
                intent.putExtra("location", arrayLocation.get(position));
                startActivity(intent);
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });

        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationAdapter.setData(arrayLocation);
            }
        });

        tvTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationAdapter.setData(arrayTrending);
            }
        });

        tvTopyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationAdapter.setData(arrayTopyear);
            }
        });

        tvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        arrayLocation.add(new Locations("Phú Yên", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, true));
        arrayLocation.add(new Locations("Đăk Lăk", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Gia Lai", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Tp Hồ Chí Minh", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Hà Nội", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Phú Yên", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Đăk Lăk", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
        arrayLocation.add(new Locations("Gia Lai", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Tp Hồ Chí Minh", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
        arrayLocation.add(new Locations("Hà Nội", "", "", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, true));

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
}