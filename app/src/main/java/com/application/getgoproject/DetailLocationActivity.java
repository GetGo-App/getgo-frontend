package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.CommentAdapter;
import com.application.getgoproject.adapter.ImageLoctionAdapter;
import com.application.getgoproject.callback.LocationCallback;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.dto.CommentDTO;
import com.application.getgoproject.models.Comment;
import com.application.getgoproject.models.ImageLocation;
import com.application.getgoproject.models.LocationComment;
import com.application.getgoproject.models.Locations;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.CommentService;
import com.application.getgoproject.service.LocationService;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailLocationActivity extends AppCompatActivity {

    private LocationService locationService;
    private CommentService commentService;
    private UserService userService;
    private UserAuthentication userAuthentication;
    private String userToken;
    private String locationCity;
    private int locationId;
    private String userId;

    private ListView lvComment;
    private ArrayList<Comment> arrayComment;
    private CommentAdapter adapter;
    private ImageButton imgbtnGoback;
    private TextView etNameLocation, contentDescription, address, price, tvShowMore, ratingOverall;
    private ProgressBar progress5Star, progress4Star, progress3Star, progress2Star, progress1Star;
    private RatingBar ratingBarOverall, feedBackRating;
    private ArrayList<ImageLocation> arrayImage;
    private ImageLoctionAdapter imageAdapter;
    private RecyclerView recyclerImage;
    private Button btnComment;
    private TextInputEditText inputComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_local);

        userAuthentication = SharedPrefManager.getInstance(this).getUser();
        userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        locationService = retrofit.create(LocationService.class);
        commentService = retrofit.create(CommentService.class);
        userService = retrofit.create(UserService.class);

        anhXa();

        Intent intent = getIntent();
        locationId = intent.getIntExtra("detail location", 0);

        imageAdapter = new ImageLoctionAdapter(this, R.layout.layout_avatar, arrayImage);
        recyclerImage.setAdapter(imageAdapter);
        recyclerImage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        adapter = new CommentAdapter(this, R.layout.layout_cmt, arrayComment);
        lvComment.setAdapter(adapter);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapForm();
            }
        });
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listLocalForm();
            }
        });

        getLocationById(locationId, userToken, new LocationCallback() {
            @Override
            public void onLocationsFetched(List<Locations> locations) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onOneLocationsFetched(Locations locations) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locationCity = locations.getCity();
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayImage.clear();
                        if (locations.getImages() != null) {
                            for (String images : locations.getImages()) {
                                if (images != null && !images.isEmpty()) {
                                    arrayImage.add(new ImageLocation(images));
                                } else {
                                    arrayImage.add(new ImageLocation(""));
                                }
                            }
                        }
                        else {
                            arrayImage.add(new ImageLocation(""));
                        }
                        imageAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        getUserByUsername(userAuthentication.getUsername(), userToken, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                userId = user.getId();
            }
        });

        tvShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvShowMore.getText().toString().equalsIgnoreCase("Show more")) {
                    contentDescription.setMaxLines(Integer.MAX_VALUE);
                    tvShowMore.setText("Show less");
                }
                else {
                    contentDescription.setMaxLines(10);
                    tvShowMore.setText("Show more");
                }
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = inputComment.getText().toString().trim();
                float ratingNumber = feedBackRating.getRating();

                CommentDTO commentDTO = new CommentDTO(commentText, null, ratingNumber, userId, locationId);
                sendComment(commentDTO, userToken);
            }
        });
    }
    private void anhXa(){
        etNameLocation = findViewById(R.id.etNameLocation);
        contentDescription = findViewById(R.id.contentDescription);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        recyclerImage = findViewById(R.id.recyclerImage);
        lvComment = (ListView) findViewById(R.id.lvComment);
        address = findViewById(R.id.address);
        price = findViewById(R.id.price);
        tvShowMore = findViewById(R.id.tvShowMore);
        btnComment = findViewById(R.id.btnComment);
        inputComment = findViewById(R.id.inputComment);
        ratingOverall = findViewById(R.id.ratingOverall);
        progress5Star = findViewById(R.id.progress5Star);
        progress4Star = findViewById(R.id.progress4Star);
        progress3Star = findViewById(R.id.progress3Star);
        progress2Star = findViewById(R.id.progress2Star);
        progress1Star = findViewById(R.id.progress1Star);
        ratingBarOverall = findViewById(R.id.ratingBarOverall);
        feedBackRating = findViewById(R.id.feedbackRating);

        progress5Star.setMax(10000);
        progress4Star.setMax(10000);
        progress3Star.setMax(10000);
        progress2Star.setMax(10000);
        progress1Star.setMax(10000);

        arrayImage = new ArrayList<>();
//        arrayImage.add(new Image(R.drawable.sapa));
//        arrayImage.add(new Image(R.drawable.sapa));
//        arrayImage.add(new Image(R.drawable.sapa));
//        arrayImage.add(new Image(R.drawable.sapa));
//        arrayImage.add(new Image(R.drawable.sapa));

        arrayComment = new ArrayList<>();
        arrayComment.add(new Comment(R.drawable.border_gradient, "Yen Vi", "2 days ago", "Awesome place for tourist trying to have a sip of coffee. Authentic taste, quiet with beautiful vintage decorations. Recommended.", 1));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Duc Anh", "a week ago", "Beautiful view, very nice atmostphere, the food so good and cheap. Love it!", 2));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Mimi", "a week ago", "Nice people, nice view, nice price", 3));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Yen Vi", "2 days ago", "Awesome place for tourist trying to have a sip of coffee. Authentic taste, quiet with beautiful vintage decorations. Recommended.", 4));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Duc Anh", "a week ago", "Beautiful view, very nice atmostphere, the food so good and cheap. Love it!", 5));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Mimi", "a week ago", "Nice people, nice view, nice price", 1));
    }

    private void listLocalForm(){
        Intent intent = new Intent(this, ListLocationActivity.class);
        intent.putExtra("location", locationCity);
        startActivity(intent);
    }
    private void mapForm(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("location address", locationId);
        startActivity(intent);
    }

    private void getLocationById(int id, String token, LocationCallback callback) {
        Call<Locations> call = locationService.getLocationsById(id, token);
        call.enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Locations locations = response.body();

                    etNameLocation.setText(locations.getName());
                    contentDescription.setText(locations.getContent());
                    address.setText(locations.getAddress());
                    price.setText(locations.getPrice());

                    ratingOverall.setText(String.format("%.1f", locations.getWebsiteRatingOverall()));
                    ratingBarOverall.setRating(locations.getWebsiteRatingOverall());
                    progress5Star.setProgress((int) (locations.getWebsiteRating().getStar5() * 100));
                    progress4Star.setProgress((int) (locations.getWebsiteRating().getStar4() * 100));
                    progress3Star.setProgress((int) (locations.getWebsiteRating().getStar3() * 100));
                    progress2Star.setProgress((int) (locations.getWebsiteRating().getStar2() * 100));
                    progress1Star.setProgress((int) (locations.getWebsiteRating().getStar1() * 100));

                    arrayImage.clear();
                    if (locations.getImages() != null) {
                        for (String images : locations.getImages()) {
                            if (images != null && !images.isEmpty()) {
                                arrayImage.add(new ImageLocation(images));
                            } else {
                                arrayImage.add(new ImageLocation("")); // Placeholder for default image handling
                            }
                        }
                    }
                    else {
                        arrayImage.add(new ImageLocation(""));
                    }
                    imageAdapter.notifyDataSetChanged();

                    callback.onOneLocationsFetched(locations);
                }
                else {
                    Log.d("Error", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                Log.d("Error", throwable.getMessage());
            }
        });
    }

    public void getUserByUsername(String username, String token, UserCallback callback) {
        try {
            Call<User> call = userService.getUserByUsername(username, token);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();
                        callback.onUserFetched(user);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    // Handle failure
                }
            });
        }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void sendComment(CommentDTO commentDTO, String token) {
        try {
            Call<Comment> call = commentService.createComments(commentDTO, token);
            call.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful() && response.body() != null) {

                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable throwable) {
                    Log.d("Failed", throwable.getMessage());
                }
            });
        }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getLocationCommentById(int id, String token) {
        try {
            Call<List<LocationComment>> call = locationService.getListLocationCommentById(id, token);
            call.enqueue(new Callback<List<LocationComment>>() {
                @Override
                public void onResponse(Call<List<LocationComment>> call, Response<List<LocationComment>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<LocationComment> locationComments = response.body();
                    }
                }

                @Override
                public void onFailure(Call<List<LocationComment>> call, Throwable throwable) {
                    Log.d("Failed", throwable.getMessage());
                }
            });
        }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
