package com.application.getgoproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.media.ExifInterface;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Matrix;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CreateStoryActivity extends AppCompatActivity {
    private ImageButton goBack, buttonOke, buttonCancel, buttonBack;
    private ImageView imageCamera;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_story);

        mapping();

        String imagePath = getIntent().getStringExtra("image_path");
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bitmap = rotateImageIfRequired(bitmap, imagePath);
            imageCamera.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateStoryActivity.this, MainActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(CreateStoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapping() {
        goBack = findViewById(R.id.goBack);
        buttonBack = findViewById(R.id.buttonBack);
        buttonOke = findViewById(R.id.buttonOke);
        buttonCancel = findViewById(R.id.buttonCancel);
        imageCamera = findViewById(R.id.imageCamera);
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
}
