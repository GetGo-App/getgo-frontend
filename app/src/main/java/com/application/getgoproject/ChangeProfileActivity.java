package com.application.getgoproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.dto.UpdateUserDTO;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeProfileActivity extends AppCompatActivity {

    private UserAuthentication userAuthentication;
    private User user;
    private UserService userService;

    private EditText edtUsername, edtPhone, edtPassword, edtConfirmPassword, edtBirthday;
    private RadioButton btnMale, btnFemale, btnOther;
    private Button btnChange, btnCancel;
    private ImageButton imgbtnGoback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        userAuthentication = SharedPrefManager.getInstance(this).getUser();
        String userToken = "Bearer " + userAuthentication.getAccessToken();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);

        mapping();

        getUserByName(userAuthentication.getUsername(), userToken);
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userForm();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedUsername = edtUsername.getText().toString();
                String updatedPhone = edtPhone.getText().toString();
                String updatedPassword = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String updatedGender = "other";
                if (btnMale.isChecked()) {
                    updatedGender = "male";
                } else if (btnFemale.isChecked()) {
                    updatedGender = "female";
                }
                LocalDateTime updatedBirthday;
                try {
                    updatedBirthday = LocalDateTime.parse(edtBirthday.getText().toString() + "T00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss"));
                } catch (DateTimeParseException e) {
                    Toast.makeText(ChangeProfileActivity.this, "Invalid date format!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!updatedPassword.equals(confirmPassword)) {
                    Toast.makeText(ChangeProfileActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                UpdateUserDTO updateUserDTO = new UpdateUserDTO(updatedUsername, updatedPassword, updatedPhone, updatedGender, user.getEmail(), user.getAvatar() ,updatedBirthday);
                updateUserByName(user.getUserName(), updateUserDTO, userToken);

                userForm();
//                Toast.makeText(ChangeProfileActivity.this, "Change infomation successful!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelForm();
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
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtBirthday = findViewById(R.id.edtBirthday);

        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);
        btnOther = findViewById(R.id.btnOther);
        btnChange = findViewById(R.id.btnChange);
        btnCancel = findViewById(R.id.btnCancel);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
    }
    private void cancelForm() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
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

    private void getUserByName(String username, String token) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;

        Call<User> call = userService.getUserByUsername(username, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user = response.body();
                    edtUsername.setText(user.getUserName() != null ? user.getUserName() : "");
                    edtPhone.setText(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");
                    if (user.getBirthday() != null && !user.getBirthday().isEmpty()) {
                        LocalDateTime birthdayDateTime = LocalDateTime.parse(user.getBirthday(), isoFormatter);
                        LocalDate birthday = birthdayDateTime.toLocalDate();
                        edtBirthday.setText(birthday.format(formatter));
                    }
                    else {
                        edtBirthday.setText("");
                    }

                    if (user.getGender() != null) {
                        if (user.getGender().equalsIgnoreCase("male")) {
                            btnMale.setChecked(true);
                        }
                        else if (user.getGender().equalsIgnoreCase("female")) {
                            btnFemale.setChecked(true);
                        } else if (user.getGender().equalsIgnoreCase("other")) {
                            btnOther.setChecked(true);
                        }
                    }
                    else {
                        btnMale.setChecked(true);
                    }
                }
                else {
                    Log.d("Error", "Failed to fetch " + response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.d("Error", "Network request failed: " + throwable.getMessage());
            }
        });
    }

    private void updateUserByName(String username, UpdateUserDTO updateUserDTO, String token) {
        Call<User> call = userService.updateUserByUsername(username, updateUserDTO, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user = response.body();

                    userAuthentication.setUsername(user.getUserName());
                    userAuthentication.setEmail(user.getEmail());
                    SharedPrefManager.getInstance(ChangeProfileActivity.this).saveUser(userAuthentication);
                    Toast.makeText(ChangeProfileActivity.this, "Change information successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.d("Error", "Failed to change information");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.d("Error", throwable.getMessage());
            }
        });
    }
}
