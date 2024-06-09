package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.BenefitAdapter;
import com.application.getgoproject.adapter.CommentAdapter;
import com.application.getgoproject.adapter.ImageAdapter;
import com.application.getgoproject.adapter.ImageLoctionAdapter;
import com.application.getgoproject.adapter.ListItemAdapter;
import com.application.getgoproject.models.Comment;
import com.application.getgoproject.models.Image;

import java.util.ArrayList;

public class DetailLocationActivity extends AppCompatActivity {
    private ListView lvComment;
    private ArrayList<Comment> arrayComment;
    private CommentAdapter adapter;
    private ImageButton imgbtnGoback;
    private TextView etNameHotel, contentDescription;
    private ArrayList<Image> arrayImage;
    private ImageLoctionAdapter imageAdapter;
    private RecyclerView recyclerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_local);

        anhXa();

        Intent intent = getIntent();
        String name = intent.getStringExtra("detail location");
        etNameHotel.setText(name);

        imageAdapter = new ImageLoctionAdapter(this, R.layout.layout_avatar, arrayImage);
        recyclerImage.setAdapter(imageAdapter);
        recyclerImage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        adapter = new CommentAdapter(this, R.layout.layout_cmt, arrayComment);
        lvComment.setAdapter(adapter);

        contentDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapForm();
            }
        });
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listLocalForm();
            }
        });

    }
    private void anhXa(){
        etNameHotel = findViewById(R.id.etNameHotel);
        contentDescription = findViewById(R.id.contentDescription);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        recyclerImage = findViewById(R.id.recyclerImage);
        lvComment = (ListView) findViewById(R.id.lvComment);

        arrayImage = new ArrayList<>();
        arrayImage.add(new Image(R.drawable.sapa));
        arrayImage.add(new Image(R.drawable.sapa));
        arrayImage.add(new Image(R.drawable.sapa));
        arrayImage.add(new Image(R.drawable.sapa));
        arrayImage.add(new Image(R.drawable.sapa));

        arrayComment = new ArrayList<>();
        arrayComment.add(new Comment(R.drawable.border_gradient, "Yen Vi", "2 days ago", "Awesome place for tourist trying to have a sip of coffee. Authentic taste, quiet with beautiful vintage decorations. Recommended.", 1));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Duc Anh", "a week ago", "Beautiful view, very nice atmostphere, the food so good and cheap. Love it!", 2));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Mimi", "a week ago", "Nice people, nice view, nice price", 3));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Yen Vi", "2 days ago", "Awesome place for tourist trying to have a sip of coffee. Authentic taste, quiet with beautiful vintage decorations. Recommended.", 4));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Duc Anh", "a week ago", "Beautiful view, very nice atmostphere, the food so good and cheap. Love it!", 5));
        arrayComment.add(new Comment(R.drawable.border_gradient, "Mimi", "a week ago", "Nice people, nice view, nice price", 1));
    }

    private void listLocalForm(){
        Intent intent = new Intent(this, ListLocationlActivity.class);
        startActivity(intent);
        finish();
    }
    private void mapForm(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }
}
