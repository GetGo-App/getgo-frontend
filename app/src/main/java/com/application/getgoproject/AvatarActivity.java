package com.application.getgoproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.AvatarAdapter;
import com.application.getgoproject.models.Avatar;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.utils.SharedPrefManager;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AvatarActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PAGE_SIZE = 6;
    private Button btnUpload, btnSave;
    private TextView tvName;
    private ShapeableImageView avatar;
    private ImageView imgbtnGoback;
    private ArrayList<Avatar> arrayAvatar;
    private AvatarAdapter avatarAdapter;
    private RecyclerView recyclerAvatar;
    private int index;
    private boolean isLoading = false;
    private int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avatar_dialog);

        mapping();

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        tvName.setText(userAuthentication.getUsername());

        avatarAdapter = new AvatarAdapter(this, R.layout.layout_avatar, arrayAvatar);
        recyclerAvatar.setAdapter(avatarAdapter);
        recyclerAvatar.setLayoutManager(new GridLayoutManager(this,2));

        avatarAdapter.setOnItemClickListener(new AvatarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                index = position;
                avatarAdapter.setSelectedPosition(position);

                Avatar selectedAvatar = arrayAvatar.get(position);
                if (selectedAvatar != null) {
                    avatar.setImageURI(selectedAvatar.getImgAvatarUri());
                }
                else {
                    avatar.setImageResource(R.drawable.avatar);
                }
            }
        });

        recyclerAvatar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == arrayAvatar.size() - 1) {
                    loadMoreImages();
                    isLoading = true;
                }
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvatarActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        checkPermissionsAndLoadImages();
    }

    private void loadMoreImages() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);

                if (cursor != null) {
                    int startIndex = currentPage * PAGE_SIZE;
                    int endIndex = Math.min(startIndex + PAGE_SIZE, cursor.getCount());
                    cursor.moveToPosition(startIndex - 1);

                    while (cursor.getPosition() < endIndex && cursor.moveToNext()) {
                        String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        arrayAvatar.add(new Avatar(Uri.parse(imagePath)));
                    }

                    cursor.close();
                }

                avatarAdapter.notifyDataSetChanged();
                isLoading = false;
                currentPage++;
            }
        }, 1500);
    }

    private void checkPermissionsAndLoadImages() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            loadImagesFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImagesFromGallery();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadImagesFromGallery() {
        loadMoreImages();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            if (imageUri != null) {
                avatar.setImageURI(imageUri);
            }
        }
    }

    private void mapping() {
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        avatar = findViewById(R.id.avatar);
        recyclerAvatar = findViewById(R.id.recyclerAvatar);
        tvName = findViewById(R.id.tvName);

        arrayAvatar = new ArrayList<>();
//        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
//        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
//        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
//        arrayAvatar.add(new Avatar(R.drawable.border_gradient));
//        arrayAvatar.add(new Avatar(R.drawable.startbackground));
//        arrayAvatar.add(new Avatar(R.drawable.startbackground));
//        arrayAvatar.add(new Avatar(R.drawable.startbackground));
//        arrayAvatar.add(new Avatar(R.drawable.startbackground));
//        arrayAvatar.add(new Avatar(R.drawable.sapa));
//        arrayAvatar.add(new Avatar(R.drawable.sapa));
//        arrayAvatar.add(new Avatar(R.drawable.sapa));
//        arrayAvatar.add(new Avatar(R.drawable.startbackground));
//        arrayAvatar.add(new Avatar(R.drawable.sapabackground));
//        arrayAvatar.add(new Avatar(R.drawable.sapa));

    }
}
