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

public class ForgetPasswordActivity extends AppCompatActivity {

    private UserService userService;

    private ImageButton btnGoback;
    private Button btnSend;
    private EditText edEmail;
    private final String REQUIRE = "Require";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgetpassword);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);

        anhXa();

        btnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInForm();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();

            }
        });
    }
    private void anhXa() {
        btnGoback = findViewById(R.id.imgbtnGoback);
        btnSend = findViewById(R.id.btnSend);
        edEmail = findViewById(R.id.edEmail);
    }

    private boolean checkInput() {
        // email
        if (TextUtils.isEmpty(edEmail.getText().toString())){
            edEmail.setError((REQUIRE));
            return false;
        }
        //Valid
        return true;
    }

    private void send(){
        //Invalid
        if (!checkInput()) {
            Toast.makeText(ForgetPasswordActivity.this,"Please enter your email or phone number!", Toast.LENGTH_SHORT).show();
            return ;
        }

        newpassForm();
    }

    private void signInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void newpassForm() {
        try {
            Call<ResponseBody> call = userService.sendEmailForgetPassword(edEmail.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(ForgetPasswordActivity.this, NewPasswordActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        edEmail.setError("Cannot find email or phone number");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Toast.makeText(ForgetPasswordActivity.this, "Failed to send email or phone number", Toast.LENGTH_LONG).show();
                    throwable.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(ForgetPasswordActivity.this, "An error has been occurred", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
