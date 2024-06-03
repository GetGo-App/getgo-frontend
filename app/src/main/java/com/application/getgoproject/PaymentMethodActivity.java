package com.application.getgoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
    private ListView lvMethod;
    private PaymentMethodAdapter paymentAdapt;
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

        paymentAdapt = new PaymentMethodAdapter(this, R.layout.layout_payment_method, arrayPayment);
        lvMethod.setAdapter(paymentAdapt);

        lvMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PaymentMethodActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
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
        lvMethod = findViewById(R.id.lvMethod);

        arrayPayment = new ArrayList<>();
        arrayPayment.add(new ListItem(R.drawable.vnpay,"VN Pay",""));
        arrayPayment.add(new ListItem(R.drawable.momo,"Momo",""));
        arrayPayment.add(new ListItem(R.drawable.add_card,"Card",""));
    }
}
