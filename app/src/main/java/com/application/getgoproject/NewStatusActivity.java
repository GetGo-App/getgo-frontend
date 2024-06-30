package com.application.getgoproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.NewStatusImageAdapter;
import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.dto.StatusDTO;
import com.application.getgoproject.models.ImageNewStatus;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.StatusService;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewStatusActivity extends AppCompatActivity {
    private static final int PICK_IMAGES_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 2;

    private StatusService statusService;
    private UserService userService;

    private ImageButton imgbtnGoback, imgAddImage, imgCamera;
    private Button btnUpload;
    private TextInputEditText contentStatus;
    private ShapeableImageView avatar;
    private TextView username;
    private RecyclerView recyclerImage;
    private NewStatusImageAdapter adapter;
    private ArrayList<ImageNewStatus> imageArray;
    private ArrayList<Uri> imageUris;
    private String userToken;
    private String userId;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status);

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        statusService = retrofit.create(StatusService.class);
        userService = retrofit.create(UserService.class);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("statuses");
        mAuth = FirebaseAuth.getInstance();

        mapping();
        username.setText(userAuthentication.getUsername());

        adapter = new NewStatusImageAdapter(this, R.layout.layout_image_newstatus, imageArray);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);

        recyclerImage.setAdapter(adapter);
        recyclerImage.setLayoutManager(gridLayoutManager);

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusForm();
            }
        });

        imgAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(NewStatusActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewStatusActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    addImage();
                }
            }
        });

//        imgCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cameraForm();
//            }
//        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageToFireBase();
            }
        });

        getUserByName(userAuthentication.getUsername(), userToken, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                userId = user.getId();

                if (user.getAvatar() != null) {
                    Glide.with(NewStatusActivity.this)
                            .load(user.getAvatar())
                            .into(avatar);
                }
                else {
                    avatar.setImageResource(R.drawable.avatar);
                }
            }
        });
    }
    private void statusForm(){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
        finish();
    }

    private void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE);
    }

//    private void cameraForm() {
//        Intent intent = new Intent(this, CameraActivity.class);
//        startActivity(intent);
//        finish();
//    }
    private void mapping(){
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        imgAddImage = findViewById(R.id.imgAddImage);
//        imgCamera = findViewById(R.id.imgCamera);
        btnUpload = findViewById(R.id.btnUpload);
        contentStatus = findViewById(R.id.contentStatus);
        username = findViewById(R.id.username);
        avatar = findViewById(R.id.avatar);

        recyclerImage = findViewById(R.id.recyclerImage);

        imageArray = new ArrayList<>();
        imageUris = new ArrayList<>();

//        imageArray.add(new ImageNewStatus(R.drawable.sapa));
//        imageArray.add(new ImageNewStatus(R.drawable.sapa));
//        imageArray.add(new ImageNewStatus(R.drawable.sapa));
    }

    private void saveImageToFireBase() {
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadImageToFireBase();
                        }
                        else {
                            Log.w("NewStatusActivity", "signInAnonymously:failure", task.getException());
                            Toast.makeText(NewStatusActivity.this, "Upload status failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                uploadImageToFireBase();
            }
        }
        catch (Exception e) {
            Log.e("Error save status images", e.getMessage());
        }
    }

    private void uploadImageToFireBase() {
        if (imageUris.isEmpty()) {
            createNewStatusWithImages(new ArrayList<>());
            return;
        }

        List<String> imageUrls = new ArrayList<>();
        for (Uri imageUri : imageUris) {
            StorageReference imageRef = storageReference.child("images/" + UUID.randomUUID().toString() + ".jpg");

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrls.add(uri.toString());
                                    if (imageUrls.size() == imageUris.size()) {
                                        createNewStatusWithImages(imageUrls);
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewStatusActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void createNewStatusWithImages(List<String> imageUrls) {
        String content = contentStatus.getText().toString();
        String uploader = userId;
        String privacyMode = "public";

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setContent(content);
        statusDTO.setImages(imageUrls);
        statusDTO.setUploader(uploader);
        statusDTO.setPrivacyMode(privacyMode);

        createNewStatus(statusDTO, userToken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageArray.add(new ImageNewStatus(imageUri));
                        imageUris.add(imageUri);
                    }
                }
                else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    imageArray.add(new ImageNewStatus(imageUri));
                    imageUris.add(imageUri);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addImage();
            }
            else {
                Toast.makeText(this, "Permission denied!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserByName(String username, String token, UserCallback callback) {
        Call<User> call = userService.getUserByUsername(username, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    callback.onUserFetched(user);
                }
                else {
                    Log.d("Error", "Failed to fetch");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
    }

    private void createNewStatus(StatusDTO statusDTO, String token) {
        Call<ResponseBody> call = statusService.createStatus(statusDTO, token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NewStatusActivity.this, "Created new status successfully", Toast.LENGTH_SHORT).show();
                    statusForm();
                } else {
                    Toast.makeText(NewStatusActivity.this, "Failed to create status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(NewStatusActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
