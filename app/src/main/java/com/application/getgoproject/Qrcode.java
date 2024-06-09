package com.application.getgoproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Qrcode extends Fragment {
    private ImageView imgQR;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_qrcode, container, false);
        imgQR = view.findViewById(R.id.imgQR);
        String content = "Hoang Le Huong";
        MultiFormatWriter mWrite = new MultiFormatWriter();
        try {
            BitMatrix mMatrix = mWrite.encode(content, BarcodeFormat.QR_CODE, 400, 400);

            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
            imgQR.setImageBitmap(mBitmap);

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        return view;

    }
}
