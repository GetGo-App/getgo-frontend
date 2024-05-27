package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignInActivity extends AppCompatActivity {
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
        Toast.makeText(SignInActivity.this,"Sign in successful!", Toast.LENGTH_SHORT).show();
        homeForm();
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
}
