package com.application.getgoproject;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.utils.SharedPrefManager;

public class StartActivity extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    ImageButton nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (isUserSignedIn()) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        mapping();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateTextChange();
            }
        });
    }

    private void mapping() {
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        nextButton = findViewById(R.id.nextButton);
    }

    @SuppressLint("SetTextI18n")
    private void animateTextChange() {
        if (textView.getText().toString().equals("Let's start") && textView1.getText().toString().equals("your Vacation")) {
            // Animate the first text change
            textView.setText("Whole new");
            textView1.setText("way to Travel");

            ObjectAnimator textViewAnimator = ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
            textViewAnimator.setDuration(1000);

            ObjectAnimator textView1Animator = ObjectAnimator.ofFloat(textView1, "alpha", 0.0f, 1.0f);
            textView1Animator.setDuration(1000);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(textViewAnimator, textView1Animator);
            animatorSet.start();
        } else {
            // Animate the transition to the LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_fade, R.anim.activity_fade);
            finish();
        }
    }

    private boolean isUserSignedIn() {
        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        return userAuthentication != null && userAuthentication.getAccessToken() != null;
    }
}
