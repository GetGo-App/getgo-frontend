package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewPasswordActivity extends AppCompatActivity {

    private UserService userService;

    private ImageButton btnGoback;
    private Button btnVerify;
    private EditText etVerify, etPassword;
    private final String REQUIRE = "Require";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_newpassword);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);

        anhXa();

        btnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetForm();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();

            }
        });
    }
    private void anhXa() {
        btnGoback = findViewById(R.id.imgbtnGoback);
        btnVerify = findViewById(R.id.btnVerify);
        etVerify = findViewById(R.id.etVerify);
        etPassword = findViewById(R.id.etPassword);
    }

    private boolean checkInput() {
        // verification code
        if (TextUtils.isEmpty(etVerify.getText().toString())){
            etVerify.setError((REQUIRE));
            return false;
        }
        // password
        if (TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError((REQUIRE));
            return false;
        }
        //Valid
        return true;
    }

    private void verify(){
        //Invalid
        if (!checkInput()) {
            return ;
        }
//        Toast.makeText(NewPasswordActivity.this,"Create new password successful!", Toast.LENGTH_SHORT).show();
        verifyForm();
    }

    private void forgetForm() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    private void verifyForm() {
        try {
            Call<ResponseBody> call = userService.newPassword(etPassword.getText().toString(), etVerify.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(NewPasswordActivity.this,"Create new password successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewPasswordActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                    }
                    else {
                        etVerify.setError("Wrong OTP Code");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Toast.makeText(NewPasswordActivity.this, "Failed to verify code or update new password", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(NewPasswordActivity.this, "An error has been occurred", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}