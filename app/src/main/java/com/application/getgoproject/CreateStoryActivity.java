package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Matrix;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateStoryActivity extends AppCompatActivity {

    private UserService userService;

    private String username, userToken;
    private ShapeableImageView avatar;
    private ImageButton goBack, buttonOke, buttonCancel, buttonBack;
    private ImageView imageCamera;
    private Bitmap storyImageBitmap;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_story);

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        username = userAuthentication.getUsername();
        userToken = userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);

        mapping();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("stories");
        mAuth = FirebaseAuth.getInstance();

        String imagePath = getIntent().getStringExtra("image_path");
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bitmap = rotateImageIfRequired(bitmap, imagePath);
            imageCamera.setImageBitmap(bitmap);
            storyImageBitmap = bitmap;
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStoryImageToFirebase();
//                Intent intent = new Intent(CreateStoryActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUserByUsername(username, userToken, new UserCallback() {
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

    private void mapping() {
        goBack = findViewById(R.id.goBack);
        buttonBack = findViewById(R.id.buttonBack);
        buttonOke = findViewById(R.id.buttonOke);
        buttonCancel = findViewById(R.id.buttonCancel);
        imageCamera = findViewById(R.id.imageCamera);
        avatar = findViewById(R.id.avatar);
    }
    private Bitmap rotateImageIfRequired(Bitmap img, String imagePath) {
        ExifInterface ei;
        try {
            ei = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return img;
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
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
                    throwable.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            Log.d("Error in Camera", e.getMessage());
        }
    }

    private void saveStoryImageToFirebase() {
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadStoryImageToFirebase();
                        }
                        else {
                            Log.w("CreateStoryActivity", "signInAnonymously:failure", task.getException());
                            Toast.makeText(CreateStoryActivity.this, "Save image failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                uploadStoryImageToFirebase();
            }
        }
        catch (Exception e) {
            Log.e("Error save image", e.getMessage());
        }
    }

    private void uploadStoryImageToFirebase() {
        try {
            if (storyImageBitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                storyImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                StorageReference storyImagesRef = storageReference.child("story_" + username + "_" + System.currentTimeMillis() + ".jpg");

                UploadTask uploadTask = storyImagesRef.putBytes(data);
                uploadTask.addOnFailureListener(exception -> {
                    Toast.makeText(CreateStoryActivity.this, "Failed to upload image: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(taskSnapshot -> storyImagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    Toast.makeText(CreateStoryActivity.this, "Story uploaded successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateStoryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }));
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(CreateStoryActivity.this, "An error has been occurred when upload story image", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
