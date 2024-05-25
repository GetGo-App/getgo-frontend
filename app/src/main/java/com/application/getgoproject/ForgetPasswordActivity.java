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

public class ForgetPasswordActivity extends AppCompatActivity {
    private ImageButton btnGoback;
    private Button btnSend;
    private EditText edEmail;
    private final String REQUIRE = "Require";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgetpassword);

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
            return ;
        }
        Toast.makeText(ForgetPasswordActivity.this,"Please attend your mail!", Toast.LENGTH_SHORT).show();
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
