package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnSignIn, btnSignUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInForm();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpForm();
            }
        });
    }
    private void signInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
    private void signUpForm() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    private void anhXa() {
        btnSignIn = findViewById(R.id.signInButton);
        btnSignUp = findViewById(R.id.signUpButton);
    }
}
