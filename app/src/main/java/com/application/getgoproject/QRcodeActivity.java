package com.application.getgoproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRcodeActivity extends AppCompatActivity {
    private ImageButton imageBtnHome, btnQRcode, btnScancode;
    private static final int CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mapping();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.QRcontent, new Qrcode())
                    .commit();
        }

        btnQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Qrcode());
            }
        });

        btnScancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    replaceFragment(new Scancode());
                } else {
                    requestPermission();
                }
            }
        });
        imageBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.QRcontent,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(QRcodeActivity.this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Camera permission is needed to scan QR codes", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                replaceFragment(new Scancode());
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void mapping() {
        imageBtnHome = findViewById(R.id.imageBtnHome);
        btnQRcode = findViewById(R.id.btnQRcode);
        btnScancode = findViewById(R.id.btnScancode);
    }
    private void homeForm(){
        Intent intent = new Intent(QRcodeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
