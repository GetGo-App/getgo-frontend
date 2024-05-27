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

public class NewPasswordActivity extends AppCompatActivity {
    private ImageButton btnGoback;
    private Button btnSignin;
    private EditText etVerify, etPassword;
    private final String REQUIRE = "Require";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_newpassword);

        anhXa();

        btnGoback.setOnClickListener(new View.OnClickListener() {
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
    }
    private void anhXa() {
        btnGoback = findViewById(R.id.imgbtnGoback);
        btnSignin = findViewById(R.id.btnSignin);
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

    private void signIn(){
        //Invalid
        if (!checkInput()) {
            return ;
        }
        Toast.makeText(NewPasswordActivity.this,"Create new password successful!", Toast.LENGTH_SHORT).show();
        signInForm();
    }

    private void forgetForm() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}