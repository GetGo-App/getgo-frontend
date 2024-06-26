package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.adapter.ListLocationAdapter;
import com.application.getgoproject.adapter.LocationAdapter;
import com.application.getgoproject.callback.LocationCallback;
import com.application.getgoproject.models.Locations;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.LocationService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListLocationActivity extends AppCompatActivity {

    private LocationService locationService;
    private UserAuthentication userAuthentication;
    private String userToken;
    private ListView lvListLocation;
    private ListLocationAdapter adapter;
    private ImageButton imgbtnGoback;
    private SearchView etSearchLocation;
    private TextView tvNamePlace;
    private TextView tvAll, tvTrending, tvTopyear;
    private ArrayList<Locations> arrayLocation, arrayTopyear, arrayTrending, arrayFavor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_locations);

        userAuthentication = SharedPrefManager.getInstance(this).getUser();
        userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        locationService = retrofit.create(LocationService.class);

        mapping();
        Intent intent = getIntent();
        String namePlace = intent.getStringExtra("location");
        tvNamePlace.setText(namePlace);

        adapter = new ListLocationAdapter(this, R.layout.layout_item_locations, arrayLocation);
        lvListLocation.setAdapter(adapter);

        lvListLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListLocationActivity.this, DetailLocationActivity.class);
                intent.putExtra("detail location",arrayLocation.get(position).getId());
                startActivity(intent);
            }
        });
        updateTextViewStyles(tvAll, tvTrending, tvTopyear);
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvAll, tvTrending, tvTopyear);
                adapter.setData(arrayLocation);
            }
        });

        tvTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvTrending, tvTopyear, tvAll);
                adapter.setData(arrayTrending);
            }
        });

        tvTopyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewStyles(tvTopyear, tvAll, tvTrending);
                adapter.setData(arrayTopyear);
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationForm();
            }
        });

        etSearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
//                searchAction(etSearchLocation.getQuery().toString().trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Locations> filterLocal = new ArrayList<>();
                for (Locations location : arrayLocation) {
                    if (location.getName().contains(newText.trim())) {
                        filterLocal.add(location);
                    }
                }
                ListLocationAdapter loctionAdapter = new ListLocationAdapter(ListLocationActivity.this, R.layout.layout_item_locations, filterLocal);
                lvListLocation.setAdapter(loctionAdapter);
                return false;
            }
        });


        getLocationsByCity(namePlace, userToken, new LocationCallback() {
            @Override
            public void onLocationsFetched(List<Locations> locations) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
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
    private void mapping(){
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        etSearchLocation = findViewById(R.id.etSearchLocation);
        lvListLocation = (ListView) findViewById(R.id.lvLocation);
        tvAll = findViewById(R.id.tvAll);
        tvTrending = findViewById(R.id.tvTrending);
        tvTopyear = findViewById(R.id.tvTopyear);
        tvNamePlace = findViewById(R.id.tvNamePlace);

        arrayLocation = new ArrayList<>();
        arrayTopyear = new ArrayList<>();
        arrayTrending = new ArrayList<>();
        arrayFavor = new ArrayList<>();

    }

//    private void searchAction(String query) {
//        Toast.makeText(this, "Tìm kiếm: " + query, Toast.LENGTH_SHORT).show();
//        //gọi API
//    }

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
    
    private void getLocationsByCity(String namePlace, String token, LocationCallback callback) {
        Call<List<Locations>> call = locationService.getLocationsByCity(namePlace, token);
        call.enqueue(new Callback<List<Locations>>() {
            @Override
            public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Locations> locationsList = response.body();

                    arrayLocation.clear();
                    arrayTrending.clear();
                    arrayTopyear.clear();

                    for (Locations locations : locationsList) {
                        arrayLocation.add(new Locations(locations.getId(),
                                locations.getName(), locations.getAddress(),
                                locations.getContent(), locations.getCity(),
                                locations.getShortDescription(),
                                locations.getLatitude(), locations.getLongitude(),
                                locations.getImages(), locations.isAvailable(),
                                locations.getOpenTime(), locations.getDetailUrl(),
                                locations.getHotline(), locations.getPrice(),
                                locations.getCategoryId(), locations.getRating(),
                                locations.getWebsiteRating(), locations.getWebsiteRatingOverall(),
                                locations.isTrend(), locations.isTopYear()));
                    }

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
                Log.d("Error", throwable.getMessage());
            }
        });
    }
}
