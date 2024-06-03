package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.BenefitAdapter;
import com.application.getgoproject.adapter.PackageAdapter;
import com.application.getgoproject.models.ListItem;
import com.application.getgoproject.models.ListPackage;

import java.util.ArrayList;

public class PackageActivity extends AppCompatActivity {
    private Button btnNext;
    private ImageView imgbtnGoback;
    private ArrayList<ListPackage> arrayPackage;
    private ArrayList<ListItem> arrayBenefit;
    private BenefitAdapter benefitAdapter;
    private PackageAdapter packageAdapter;
    private RecyclerView recyclerBenefit, recyclerPackage;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_package);

        mapping();

        benefitAdapter = new BenefitAdapter(this, R.layout.layout_item_benefit, arrayBenefit);
        recyclerBenefit.setAdapter(benefitAdapter);
        recyclerBenefit.setLayoutManager(new LinearLayoutManager(this));

        packageAdapter = new PackageAdapter(this, R.layout.layout_package_time, arrayPackage);
        recyclerPackage.setAdapter(packageAdapter);
        recyclerPackage.setLayoutManager(new LinearLayoutManager(this));


        packageAdapter.setOnItemClickListener(new PackageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                index = position;
                packageAdapter.setSelectedPosition(position);

                ListPackage clickedItem = arrayPackage.get(position);
            }
        });

        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PackageActivity.this, PaymentMethodActivity.class);
                intent.putExtra("packageTime", arrayPackage.get(index));
                startActivity(intent);
            }
        });

    }

    private void mapping() {
        btnNext = findViewById(R.id.btnNext);
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        recyclerBenefit = findViewById(R.id.recyclerBenefit);
        recyclerPackage = findViewById(R.id.recyclerPackage);

        arrayBenefit = new ArrayList<>();
        arrayBenefit.add(new ListItem(R.drawable.no_add, "No ads", ""));
        arrayBenefit.add(new ListItem(R.drawable.add_location_alt, "Detailed and customized roadmap", "The roadmap is built in detail by day, with activities and places to eat, rest, and specifically recommended entertainment."));
        arrayBenefit.add(new ListItem(R.drawable.dinner_dining, "Suggested places to eat", "Customers receive suggestions from AI about places to eat, and unique local activities."));

        arrayPackage = new ArrayList<>();
        arrayPackage.add(new ListPackage("A day", "5.000 VND"));
        arrayPackage.add(new ListPackage("A month", "30.000 VND"));
        arrayPackage.add(new ListPackage("A year", "250.000 VND"));
    }

    private void homeForm() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void payForm() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}