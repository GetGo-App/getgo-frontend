package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.adapter.LocationAdapter;
import com.application.getgoproject.adapter.TransactionAdapter;
import com.application.getgoproject.models.Locations;
import com.application.getgoproject.models.Transaction;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    private RecyclerView recyclerTransaction;
    private TransactionAdapter adapter;
    private ArrayList<Transaction> arrayTransaction;
    private ImageButton imgbtnGoback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        mapping();

        adapter = new TransactionAdapter(this, R.layout.layout_transaction, arrayTransaction);
        recyclerTransaction.setAdapter(adapter);
        recyclerTransaction.setLayoutManager(new LinearLayoutManager(this));
        imgbtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeForm();
            }
        });
    }
    private void mapping(){
        imgbtnGoback = findViewById(R.id.imgbtnGoback);
        recyclerTransaction = findViewById(R.id.recyclerTransaction);
        arrayTransaction = new ArrayList<>();
        arrayTransaction.add(new Transaction("A day","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A day","11/05/24, 7:02:12", "5.000 VND"));
        arrayTransaction.add(new Transaction("A day","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A day","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A month","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A month","11/05/24, 7:02:12", "5.000 VND"));
        arrayTransaction.add(new Transaction("A month","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A month","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A year","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A year","11/05/24, 7:02:12", "5.000 VND"));
        arrayTransaction.add(new Transaction("A year","15/05/24, 9:49:24", "5.000 VND"));
        arrayTransaction.add(new Transaction("A year","15/05/24, 9:49:24", "5.000 VND"));
    }
    private void homeForm(){
        Intent intent = new Intent(TransactionActivity.this, UserActivity.class);
        startActivity(intent);
        finish();
    }
}
