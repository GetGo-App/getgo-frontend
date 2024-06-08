package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.getgoproject.dto.SignInDto;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.SignInService;
import com.application.getgoproject.utils.JwtUtils;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity {

    private SignInService signInService;

    private TextView tvsignup, tvForget;
    private EditText edEmail, edPassword;
    private Button btnGg, btnSignin;
    private ImageButton btnFacebook, btnTwitter;

    private final String REQUIRE = "Require";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        signInService = retrofit.create(SignInService.class);

        anhXa();

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpForm();
            }
        });
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetForm();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btnGg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this,"Sign in with Google", Toast.LENGTH_SHORT).show();
                homeForm();
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this,"Sign in with Facebook", Toast.LENGTH_SHORT).show();
                homeForm();
            }
        });
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this,"Sign in with Twitter", Toast.LENGTH_SHORT).show();
                homeForm();
            }
        });
    }
    private void anhXa() {
        tvsignup = findViewById(R.id.tvsignup);
        tvForget = findViewById(R.id.tvForget);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnGg = findViewById(R.id.btnGg);
        btnFacebook = findViewById(R.id.btnfacebook);
        btnTwitter = findViewById(R.id.btntwitter);
        btnSignin = findViewById(R.id.btnSignin);
    }

    private boolean checkInput() {
        // email
        if (TextUtils.isEmpty(edEmail.getText().toString())){
            edEmail.setError((REQUIRE));
            return false;
        }

        //Password
        if (TextUtils.isEmpty(edPassword.getText().toString())){
            edPassword.setError((REQUIRE));
            return false;
        }

        //Valid
        return true;
    }

    private void signIn(){
        //Invalid
        if (!checkInput()) {
            return ;
        }

        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        SignInDto signInDto = new SignInDto(email, password);
        signInUser(signInDto);

//        Toast.makeText(SignInActivity.this,"Sign in successful!", Toast.LENGTH_SHORT).show();
//        homeForm();
    }
    private void homeForm() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void forgetForm() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
        finish();
    }
    private void signUpForm() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInUser(SignInDto signInDto) {
        Log.d("User sign in", signInDto.getEmail() + "-" + signInDto.getPassword());
        Call<UserAuthentication> call = signInService.signInUser(signInDto);
        call.enqueue(new Callback<UserAuthentication>() {
            @Override
            public void onResponse(Call<UserAuthentication> call, Response<UserAuthentication> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserAuthentication userAuthentication = response.body();

                    String username = JwtUtils.getUsernameFromToken(userAuthentication.getAccessToken());
                    String role = JwtUtils.getRoleFromToken(userAuthentication.getAccessToken());

                    if (username != null && role != null) {
                        if ("Customer".equals(role)) {
                            SharedPrefManager.getInstance(SignInActivity.this).saveUser(userAuthentication);
                            Toast.makeText(SignInActivity.this,"Sign in successful!", Toast.LENGTH_SHORT).show();
                            homeForm();
                        }
                    }
                    else {
                        Toast.makeText(SignInActivity.this,"Failed to get user", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Invalid email or password!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserAuthentication> call, Throwable throwable) {
                Toast.makeText(SignInActivity.this, "An error has been occurred!", Toast.LENGTH_LONG).show();
                Log.d("Login Error!!", throwable.getMessage());
            }
        });
    }
}
