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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.dto.GoogleAuthenticationDto;
import com.application.getgoproject.dto.SignInDto;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.GoogleAuthenticationService;
import com.application.getgoproject.service.SignInService;
import com.application.getgoproject.service.SignUpService;
import com.application.getgoproject.utils.JwtUtils;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity {

    //region GG AUTHENTICATION
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    //endregion

    private SignInService signInService;
    private GoogleAuthenticationService googleAuthenticationService;

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

        //region SetUp GG Authentication
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("852575728862-mq0j0jg1ig0dief06cmenqhl65b09tsh.apps.googleusercontent.com")
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(SignInActivity.this, gso);

//        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//            }
//        });

        //endregion


        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        signInService = retrofit.create(SignInService.class);
        googleAuthenticationService = retrofit.create(GoogleAuthenticationService.class);

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
                googleSignIn();
//                homeForm();
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

    private void googleSignIn(){
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc != null){
            googleAuthentication();
            homeForm();
        }


        Intent signInIntent = gsc.getSignInIntent();

        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                googleAuthentication();
                homeForm();
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void googleAuthentication(){
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc != null){
            Call<UserAuthentication> call = googleAuthenticationService.googleAuth(new GoogleAuthenticationDto(acc.getEmail()));
            call.enqueue(new Callback<UserAuthentication>() {
                @Override
                public void onResponse(Call<UserAuthentication> call, Response<UserAuthentication> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        UserAuthentication userAuthentication = response.body();
                        SharedPrefManager.getInstance(SignInActivity.this).saveUser(userAuthentication);
                        Log.d("User token", userAuthentication.getAccessToken());
                    }
                    else if (response.code() == 400) {
                        edEmail.setError("Email has been already used");
                    }
                }

                @Override
                public void onFailure(Call<UserAuthentication> call, Throwable throwable) {
                    Log.d("Error Signup", throwable.getMessage());
                }
            });

        }
    }
}
