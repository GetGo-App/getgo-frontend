package com.application.getgoproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChangeProfileActivity extends AppCompatActivity {
    private EditText edtUsername, edtPhone, edtPassword, edtConfirmPassword, edtBirthday;
    private RadioButton btnMale, btnFemale;
    private Button btnChange;
    private ImageButton imgbtnGoback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        mapping();
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userForm();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userForm();
                Toast.makeText(ChangeProfileActivity.this, "Change infomation successful!", Toast.LENGTH_SHORT).show();
            }
        });

        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
    }
    private void mapping() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtBirthday = findViewById(R.id.edtBirthday);

        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);
        btnChange = findViewById(R.id.btnChange);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
    }
    private void userForm() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
    private void chooseDate() {
        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DATE);
        int mm = calendar.get(Calendar.MONTH);
        int yyyy = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirthday.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },yyyy, mm, dd);
        datePickerDialog.show();
    }
}
