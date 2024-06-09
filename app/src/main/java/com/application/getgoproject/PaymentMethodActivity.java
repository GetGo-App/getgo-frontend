package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.adapter.PaymentMethodAdapter;
import com.application.getgoproject.models.ListItem;
import com.application.getgoproject.models.ListPackage;

import java.util.ArrayList;

public class PaymentMethodActivity extends AppCompatActivity {
    private Button btnPrevious, btnPay;
    private TextView tvNameCustomer, tvPhoneCustomer, tvTitlePackage, tvDetailPackage;
    private RadioGroup btnRadioGroup;
    private ArrayList<ListItem> arrayPayment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        mapping();

        tvNameCustomer.setText("Hoang Le Huong");
        tvPhoneCustomer.setText("0123 456 789");

        Intent intent = getIntent();
        ListPackage packages = (ListPackage) intent.getSerializableExtra("packageTime");
        if (packages != null) {
            tvTitlePackage.setText(packages.getTitle());
            tvDetailPackage.setText(packages.getPrice());
        }


        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethodActivity.this, PackageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void mapping() {
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPay = findViewById(R.id.btnPay);
        tvNameCustomer = findViewById(R.id.tvNameCustomer);
        tvPhoneCustomer = findViewById(R.id.tvPhoneCustomer);
        tvTitlePackage = findViewById(R.id.tvTitlePackage);
        tvDetailPackage = findViewById(R.id.tvDetailPackage);
        btnRadioGroup = findViewById(R.id.btnRadioGroup);

        arrayPayment = new ArrayList<>();
        arrayPayment.add(new ListItem(R.drawable.vnpay,"VN Pay",""));
        arrayPayment.add(new ListItem(R.drawable.momo,"Momo",""));
        arrayPayment.add(new ListItem(R.drawable.add_card,"Card",""));
    }
}
