package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.LocationAdapter;
import com.application.getgoproject.callback.LocationCallback;
import com.application.getgoproject.models.Locations;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.LocationService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationActivity extends AppCompatActivity {

    private LocationService locationService;
    private UserAuthentication userAuthentication;
    private String userToken;

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

        userAuthentication = SharedPrefManager.getInstance(this).getUser();
        userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        locationService = retrofit.create(LocationService.class);

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

                Intent intent = new Intent(LocationActivity.this, ListLocationActivity.class);
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

        getAllLocations(userToken, new LocationCallback() {
            @Override
            public void onLocationsFetched(List<Locations> locations) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locationAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onOneLocationsFetched(Locations locations) {

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
//        arrayLocation.add(new Locations("Quận 1", "", "", "HCM", 0,5, R.drawable.sapa, true,"","","","","",5,true, true));
//        arrayLocation.add(new Locations("Quận 2", "", "", "HCM", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
//        arrayLocation.add(new Locations("Quận 3", "", "", "HCM", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
//        arrayLocation.add(new Locations("Hoàng Mai", "", "Hà Nội", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
//        arrayLocation.add(new Locations("Đống Đa", "", "", "Hà Nội", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
//        arrayLocation.add(new Locations("Thanh Yên", "", "", "Hà Nội", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
//        arrayLocation.add(new Locations("Đăk Lăk", "", "", "Đà Nẵng", 0,5, R.drawable.sapa, true,"","","","","",5,false, false));
//        arrayLocation.add(new Locations("Gia Lai", "", "", "Đà Nẵng", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
//        arrayLocation.add(new Locations("Tp Hồ Chí Minh", "", "Đà Nẵng", "", 0,5, R.drawable.sapa, true,"","","","","",5,true, false));
//        arrayLocation.add(new Locations("Hà Nội", "", "", "Đà Nẵng", 0,5, R.drawable.sapa, true,"","","","","",5,true, true));

        arrayTopyear = new ArrayList<>();
        arrayTrending = new ArrayList<>();
        arrayFavor = new ArrayList<>();
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

    private void getAllLocations(String token, LocationCallback callback) {
        Call<List<Locations>> call = locationService.getAllLocations(token);
        call.enqueue(new Callback<List<Locations>>() {
            @Override
            public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Locations> locationsList = response.body();

                    arrayLocation.clear();

//                    for (Locations locations : locationsList) {
//                        arrayLocation.add(new Locations(locations.getId(),
//                                locations.getName(), locations.getAddress(),
//                                locations.getContent(), locations.getCity(),
//                                locations.getLatitude(), locations.getLongitude(),
//                                locations.getImages(), locations.isAvailable(),
//                                locations.getOpenTime(), locations.getDetailUrl(),
//                                locations.getHotline(), locations.getPrice(),
//                                locations.getCategoryId(), locations.getRating(),
//                                locations.getWebsiteRating(), locations.getWebsiteRatingOverall(),
//                                locations.isTrend(), locations.isTopYear()));
//                    }
                    mergeLocationsByCity(locationsList);

                    Log.d("Array Size", arrayLocation.size() + "");

                    for (int i=0; i< arrayLocation.size(); i++ ){
                        if (arrayLocation.get(i).isTrend()) {
                            arrayTrending.add(arrayLocation.get(i));
                        }
                        if (arrayLocation.get(i).isTopYear()) {
                            arrayTopyear.add(arrayLocation.get(i));
                        }
                    }

                    callback.onLocationsFetched(arrayLocation);
                }
                else {
                    Log.d("Error", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Locations>> call, Throwable throwable) {
                Log.d("Failed!", throwable.getMessage());
            }
        });
    }

    private void mergeLocationsByCity(List<Locations> locationsList) {
        Set<String> uniqueCities = new HashSet<>();

        arrayLocation.clear();

        for (Locations location : locationsList) {
            String city = location.getCity();
            if (!uniqueCities.contains(city)) {
                uniqueCities.add(city);
                // Add only the city name and minimal information to arrayLocation
                arrayLocation.add(new Locations(location.getId(), location.getName(), location.getAddress(),
                        location.getContent(), city, location.getLatitude(), location.getLongitude(),
                        location.getImages(), location.isAvailable(), location.getOpenTime(),
                        location.getDetailUrl(), location.getHotline(), location.getPrice(),
                        location.getCategoryId(), location.getRating(), location.getWebsiteRating(),
                        location.getWebsiteRatingOverall(), location.isTrend(), location.isTopYear()));
            }
        }
    }


}