package com.application.getgoproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class StoryActivity extends AppCompatActivity {
    private ViewPager2 vpImageStory;
    private CircleIndicator3 circleIndicator;
    private ShapeableImageView avatarUser;
    private TextView nameUser;
    private ImageButton close;
    private List<String> arrayImage;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = vpImageStory.getCurrentItem();
            if (currentPosition == arrayImage.size() - 1) {
                vpImageStory.setCurrentItem(0);
            } else {
                vpImageStory.setCurrentItem(currentPosition +1 );
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        mapping();

        vpImageStory.setOffscreenPageLimit(3);
        vpImageStory.setClipToPadding(false);
        vpImageStory.setClipChildren(false);

        arrayImage = new ArrayList<>();

        vpImageStory.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });

//        arrayImage.addAll(story.getImageUrls());
//        ImageProductAdapter adapter = new ImageProductAdapter(arrayImage);
//        vpImageStory.setAdapter(adapter);
        circleIndicator.setViewPager(vpImageStory);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  void mapping() {
        vpImageStory = findViewById(R.id.vpImageStory);
        circleIndicator = findViewById(R.id.circleIndicator);
        avatarUser = findViewById(R.id.avatarUser);
        nameUser = findViewById(R.id.nameUser);
        close = findViewById(R.id.close);
    }
}
