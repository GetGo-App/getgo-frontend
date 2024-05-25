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

public class SignUpActivity extends AppCompatActivity {
    private TextView tvsignin;
    private EditText edName, edEmail, edPassword;
    private Button btnGg, btnSignup;
    private ImageButton btnFacebook, btnTwitter;
    private final String REQUIRE = "Require";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

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
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this,"Sign in with Facebook", Toast.LENGTH_SHORT).show();
            }
        });
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this,"Sign in with Twitter", Toast.LENGTH_SHORT).show();
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

    private void signUp(){
        //Invalid
        if (!checkInput()) {
            return ;
        }
        Toast.makeText(SignUpActivity.this,"Create account successful!", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }
    private void signInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
