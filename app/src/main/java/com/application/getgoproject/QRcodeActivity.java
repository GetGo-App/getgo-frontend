package com.application.getgoproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRcodeActivity extends AppCompatActivity {
    private ImageButton imageBtnHome, btnQRcode, btnScancode;
    private ImageView imgQR;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mapping();

        String content = "Hoang Le Huong";
        MultiFormatWriter mWrite = new MultiFormatWriter();
        try {
            BitMatrix mMatrix = mWrite.encode(content, BarcodeFormat.QR_CODE, 400, 400);

            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
            imgQR.setImageBitmap(mBitmap);

//            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            manager.hideSoftInputFromWindow()
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        imageBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });
        btnQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnScancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void mapping() {
        imageBtnHome = findViewById(R.id.imageBtnHome);
        imgQR = findViewById(R.id.imgQR);
        btnQRcode = findViewById(R.id.btnQRcode);
        btnScancode = findViewById(R.id.btnScancode);
    }
      private void homeForm(){
          Intent intent = new Intent(QRcodeActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
      }
}
