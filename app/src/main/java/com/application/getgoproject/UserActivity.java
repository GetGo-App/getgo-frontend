package com.application.getgoproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.ListItemAdapter;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.models.ListItem;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserActivity extends AppCompatActivity {

    private UserService userService;

    private ShapeableImageView avatar;
    private ImageButton imgBtnHome, imageBtnAdd;
    private TextView tvName;
    private RecyclerView recyclerUser, recyclerService;
    private ListItemAdapter adapterUser, adapterService;
    private ArrayList<ListItem> arrayPersonal, arrayService;
    private int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        mapping();

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
            String userName = userAuthentication.getUsername();
            String userToken = userAuthentication.getAccessToken();
            tvName.setText(userName);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);

        adapterUser = new ListItemAdapter(this, R.layout.layout_item_profile, arrayPersonal);
        recyclerUser.setAdapter(adapterUser);
        recyclerUser.setLayoutManager(new LinearLayoutManager(this));

        adapterService = new ListItemAdapter(this, R.layout.layout_item_profile, arrayService);
        recyclerService.setAdapter(adapterService);
        recyclerService.setLayoutManager(new LinearLayoutManager(this));

        adapterUser.setOnItemClickListener(new ListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                index = position;
                adapterUser.setSelectedPosition(position);

                ListItem clickedItem = arrayPersonal.get(position);
//                Toast.makeText(UserActivity.this, clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (clickedItem.getTitle().equals("Change Profile")){
                    Intent intent = new Intent(UserActivity.this, ChangeProfileActivity.class);
                    startActivity(intent);
                    finish();
                } else if (clickedItem.getTitle().equals("Transaction History")){
                    Intent intent = new Intent(UserActivity.this, TransactionActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        adapterService.setOnItemClickListener(new ListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                index = position;
                adapterService.setSelectedPosition(position);

                ListItem clickedItem = arrayService.get(position);
//                Toast.makeText(UserActivity.this, clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (clickedItem.getTitle().equals("Term of services")){
                    Intent intent = new Intent(UserActivity.this, TermOfServiceActivity.class);
                    startActivity(intent);
                    finish();
                } else if (clickedItem.getTitle().equals("Privacy policy")){
                    Intent intent = new Intent(UserActivity.this, PrivacyPolicyActivity.class);
                    startActivity(intent);
                    finish();
                } else if (clickedItem.getTitle().equals("Log out")) {
                    logout();
                }
                else if (clickedItem.getTitle().equals("Support")) {
                    openFacebookPage(UserActivity.this);
                }
            }
        });

        imageBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar();
            }
        });
        imgBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });

        getUserByUsername(userName, userToken, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                String userAvatarUrl = user.getAvatar();
                if (userAvatarUrl != null) {
                    Glide.with(getApplicationContext())
                            .load(userAvatarUrl)
                            .placeholder(R.drawable.avatar)
                            .error(R.drawable.avatar)
                            .into(avatar);
                }
                else {
                    avatar.setImageResource(R.drawable.avatar);
                }
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

    private void changeAvatar() {
        Intent intent = new Intent(UserActivity.this, AvatarActivity.class);
        startActivity(intent);
        finish();
    }
    private void mapping(){
        imgBtnHome = findViewById(R.id.imageBtnHome);
        imageBtnAdd = findViewById(R.id.imageBtnAdd);
        recyclerUser = findViewById(R.id.recyclerUser);
        recyclerService = findViewById(R.id.recyclerService);
        tvName = findViewById(R.id.tvName);
        avatar = findViewById(R.id.avatar);

        arrayPersonal = new ArrayList<>();
        arrayPersonal.add(new ListItem(R.drawable.profile, "Change Profile",""));
        arrayPersonal.add(new ListItem(R.drawable.info, "Transaction History",""));

        arrayService = new ArrayList<>();
        arrayService.add(new ListItem(R.drawable.brightness_alert, "Term of services",""));
        arrayService.add(new ListItem(R.drawable.privacy_tip, "Privacy policy",""));
        arrayService.add(new ListItem(R.drawable.chat_info, "Support",""));
        arrayService.add(new ListItem(R.drawable.move_item, "Log out",""));
    }
    private void homeForm(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void statusForm(){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }

    private void logout() {
        SharedPrefManager.getInstance(this).clear();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void openFacebookPage(Context context) {
        String facebookUrl = context.getString(R.string.facebook_page_url);
        String facebookUrlScheme = "fb://facewebmodal/f?href=" + facebookUrl;

        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo("com.facebook.katana", 0);
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrlScheme));
            context.startActivity(facebookIntent);
        } catch (Exception e) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
            context.startActivity(facebookIntent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        if (userAuthentication != null) {
            String name = userAuthentication.getUsername();
            tvName.setText(name);
        }
    }
}
