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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.dto.GoogleAuthenticationDto;
import com.application.getgoproject.dto.SignUpDto;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.GoogleAuthenticationService;
import com.application.getgoproject.service.SignUpService;
import com.application.getgoproject.utils.RetrofitClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    //region GG AUTHENTICATION
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    //endregion

    private SignUpService signUpService;
    private GoogleAuthenticationService googleAuthenticationService;

    private TextView tvsignin;
    private EditText edName, edEmail, edPassword;
    private Button btnGg, btnSignup;
    private ImageButton btnFacebook, btnTwitter;
    private final String REQUIRE = "Require";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        //region SetUp GG Authentication
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(SignUpActivity.this, gso);
        //endregion

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        signUpService = retrofit.create(SignUpService.class);
        googleAuthenticationService = retrofit.create(GoogleAuthenticationService.class);

        anhXa();
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInForm();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        btnGg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this,"Sign in with Google", Toast.LENGTH_SHORT).show();
                googleSignUp();
//                homeForm();
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this,"Sign in with Facebook", Toast.LENGTH_SHORT).show();
                homeForm();
            }
        });
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this,"Sign in with Twitter", Toast.LENGTH_SHORT).show();
                homeForm();
            }
        });
    }
    private void anhXa() {
        tvsignin = findViewById(R.id.tvsignin);
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnGg = findViewById(R.id.btnGg);
        btnFacebook = findViewById(R.id.btnfacebook);
        btnTwitter = findViewById(R.id.btntwitter);
        btnSignup = findViewById(R.id.btnSignup);
    }
    private boolean checkInput() {
        // Name
        if (TextUtils.isEmpty(edName.getText().toString())){
            edName.setError((REQUIRE));
            return false;
        }

        // Email
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

    private void homeForm() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void signUp(){
        //Invalid
        if (!checkInput()) {
            return ;
        }
        String email = edEmail.getText().toString();
        String username = edName.getText().toString();
        String password = edPassword.getText().toString();

        SignUpDto signUpDto = new SignUpDto(username, email, password);
        signUpUser(signUpDto);

//        Toast.makeText(SignUpActivity.this,"Create account successful!", Toast.LENGTH_SHORT).show();
//        signInForm();
    }
    private void signInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void signUpUser(SignUpDto signUpDto) {
        Log.d("User data", signUpDto.getUserName() + "-" + signUpDto.getEmail() + "-" + signUpDto.getPassword());
        Call<UserAuthentication> call = signUpService.signUpUser(signUpDto);
        call.enqueue(new Callback<UserAuthentication>() {
            @Override
            public void onResponse(Call<UserAuthentication> call, Response<UserAuthentication> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserAuthentication userAuthentication = response.body();

                    Log.d("User token", userAuthentication.getAccessToken());

                    Toast.makeText(SignUpActivity.this,"Create account successful!", Toast.LENGTH_SHORT).show();
                    signInForm();
                }
                else if (response.code() == 400) {
                    edEmail.setError("Email has been already used");
                }
            }

            @Override
            public void onFailure(Call<UserAuthentication> call, Throwable throwable) {
                edEmail.setError("An error has been occurred");
                Log.d("Error Signup", throwable.getMessage());
            }
        });
    }

    private void googleSignUp(){
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
