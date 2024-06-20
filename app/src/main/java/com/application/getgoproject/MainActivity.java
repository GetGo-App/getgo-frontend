package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.getgoproject.callback.LocationCallback;
import com.application.getgoproject.models.Locations;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.LocationService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ShapeableImageView avatar;
    private ImageView imgPlace, imgBanner;
    private ImageButton btnAssistant, btnStatus, btnQr, imgAddStory;
    private TextView tvUsername, textPlace;
    private LocationService locationService;
    private ArrayList<Locations> arrayLocation;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        locationService = retrofit.create(LocationService.class);
        arrayLocation = new ArrayList<>();

        mapping();

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        if (userAuthentication != null) {
            String username = userAuthentication.getUsername();
            userToken = "Bearer " + userAuthentication.getAccessToken();
            tvUsername.setText(username);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        btnAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatBoxActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listStatusForm();
            }
        });
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrForm();
            }
        });

        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageForm();
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });
        imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationListForm();
            }
        });
        imgAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraForm();
            }
        });

        getAllLocations(userToken, new LocationCallback() {
            @Override
            public void onLocationsFetched(List<Locations> locations) {

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
        avatar = findViewById(R.id.avatar);
        imgPlace = findViewById(R.id.imgPlace);
        imgBanner = findViewById(R.id.imgBanner);
        btnAssistant = findViewById(R.id.btnAssistant);
        btnStatus = findViewById(R.id.btnStatus);
        btnQr = findViewById(R.id.btnQr);
        imgAddStory =findViewById(R.id.imgAddStory);
        tvUsername = findViewById(R.id.username);
        textPlace = findViewById(R.id.textPlace);
    }
    private void packageForm(){
        Intent intent = new Intent(this, PackageActivity.class);
        startActivity(intent);
        finish();
    }
    private void qrForm(){
        Intent intent = new Intent(this, QRcodeActivity.class);
        startActivity(intent);
        finish();
    }
    private void listStatusForm(){
        Intent intent = new Intent(this, ListStatusActivity.class);
        startActivity(intent);
        finish();
    }
    private void locationListForm(){
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
        finish();
    }
    private void statusForm(){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }
    private void cameraForm(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        if (userAuthentication != null) {
            String name = userAuthentication.getUsername();
            tvUsername.setText(name);
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
                    mergeLocationsByCity(locationsList);

                    Log.d("Array Size", arrayLocation.size() + "");

                    // Set random location image and city
                    if (!arrayLocation.isEmpty()) {
                        setRandomLocation();
                    }

                    callback.onLocationsFetched(arrayLocation);
                } else {
                    Log.d("Error", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Locations>> call, Throwable throwable) {
                Log.d("Failed!", throwable.getMessage());
            }
        });
    }

    private void setRandomLocation() {
        Random random = new Random();
        int locationIndex = random.nextInt(arrayLocation.size());
        Locations randomLocation = arrayLocation.get(locationIndex);

        List<String> images = randomLocation.getImages();
        String imageUrl = null;
        if (images != null && !images.isEmpty()) {
            int imageIndex = random.nextInt(images.size());
            imageUrl = images.get(imageIndex);
        }

        String cityName = randomLocation.getCity();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.sapa)
                    .into(imgPlace);
        } else {
            imgPlace.setImageResource(R.drawable.sapa);
        }

        textPlace.setText(cityName);
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
                        location.getContent(), city, location.getShortDescription(), location.getLatitude(), location.getLongitude(),
                        location.getImages(), location.isAvailable(), location.getOpenTime(),
                        location.getDetailUrl(), location.getHotline(), location.getPrice(),
                        location.getCategoryId(), location.getRating(), location.getWebsiteRating(),
                        location.getWebsiteRatingOverall(), location.isTrend(), location.isTopYear()));
            }
        }
    }
}