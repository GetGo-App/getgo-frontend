package com.application.getgoproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.dto.AvatarDTO;
import com.application.getgoproject.models.Avatar;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    private Uri selectedImageUri;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String username, userToken;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avatar_dialog);

        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());

        mapping();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("avatars");
        mAuth = FirebaseAuth.getInstance();

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        username = userAuthentication.getUsername();
        userToken = userAuthentication.getAccessToken();
        tvName.setText(username);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);

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
                    Bitmap selectedBitmap = selectedAvatar.getBitmap();
                    if (selectedBitmap != null) {
                        selectedImageUri = bitmapToUri(selectedBitmap);
                        avatar.setImageURI(selectedImageUri);
                    }
                    else {
                        avatar.setImageResource(R.drawable.avatar);
                        selectedImageUri = null;
                    }
                } else {
                    avatar.setImageResource(R.drawable.avatar);
                    selectedImageUri = null;
                }
            }
        });

        recyclerAvatar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == arrayAvatar.size() - 1) {
                    loadRecentImagesFromFirebase();
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
                saveImageToFirebase();
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

        checkPermissionsAndLoadImages();
    }

    public Uri bitmapToUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "avatar", null);
        return Uri.parse(path);
    }

    private void loadRecentImagesFromFirebase() {
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null) {
                mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase Auth", "signInAnonymously:success");
                            fetchRecentImagesFromFirebase();
                        }
                        else {
                            Log.d("Firebase Auth", "signInAnonymously:failure", task.getException());
                            isLoading = false;
                        }
                    }
                });
            }
            else {
                fetchRecentImagesFromFirebase();
            }
        }
        catch (Exception e) {
            Log.e("Firebase Error", e.getMessage());
        }
    }

    private void fetchRecentImagesFromFirebase() {
        try {
            StorageReference avatarRef = storageReference;

            avatarRef.listAll().addOnSuccessListener(listResult -> {
                for (StorageReference item : listResult.getItems()) {
                    if (item.getName().contains(username)) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            Glide.with(getApplicationContext())
                                    .asBitmap()
                                    .load(uri)
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            arrayAvatar.add(new Avatar(resource));
                                            if (arrayAvatar.size() == listResult.getItems().size()) {
                                                avatarAdapter.notifyDataSetChanged();
                                                isLoading = false;
                                            }
                                        }
                                    });
                        }).addOnFailureListener(e -> {
                            Log.e("Firebase Storage", "Failed to get download URL", e);
                        });
                    }
                }
                if (arrayAvatar.isEmpty()) {
                    avatarAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }).addOnFailureListener(e -> {
                Log.e("Firebase Storage", "Failed to list items", e);
                isLoading = false;
            });
        }
        catch (Exception e) {
            Log.e("Firebase Error", e.getMessage());
        }
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

    private void checkPermissionsAndLoadImages() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            loadRecentImagesFromFirebase();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadRecentImagesFromFirebase();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                avatar.setImageURI(selectedImageUri);
            }
        }
    }

    private void saveImageToFirebase() {
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null) {
                mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadImageToFirebase();
                        } else {
                            Log.w("AvatarActivity", "signInAnonymously:failure", task.getException());
                            Toast.makeText(AvatarActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                uploadImageToFirebase();
            }
        }
        catch (Exception e) {
            Log.e("Error save image", e.getMessage());
        }
    }

    private void uploadImageToFirebase() {
        if (selectedImageUri != null) {
            StorageReference imageRef = storageReference.child("avatar_" + username + "_" + System.currentTimeMillis() + ".jpg");

            imageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String avatarUrl = uri.toString();
                                    AvatarDTO avatarDTO = new AvatarDTO(avatarUrl);
                                    Log.d("Avatar link", avatarUrl);
                                    updateAvatarByUsername(username, avatarDTO, userToken);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AvatarActivity.this, "Failed to upload image: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAvatarByUsername(String username, AvatarDTO avatarDTO, String token) {
        try {
            Call<ResponseBody> call = userService.updateAvatarByUsername(username, avatarDTO, token);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(AvatarActivity.this, "Change avatar successfully", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Log.e("Failed to change avatar", throwable.getMessage());
                    Toast.makeText(AvatarActivity.this, "Failed to change avatar", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.e("Error update Avatar", e.getMessage());
            Toast.makeText(AvatarActivity.this, "An error has been occurred", Toast.LENGTH_LONG).show();
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
