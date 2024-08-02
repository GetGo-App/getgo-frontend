package com.application.getgoproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.getgoproject.callback.UserCallback;
import com.application.getgoproject.models.ListItem;
import com.application.getgoproject.models.ListPackage;
import com.application.getgoproject.models.User;
import com.application.getgoproject.models.UserAuthentication;
import com.application.getgoproject.service.UserService;
import com.application.getgoproject.utils.RetrofitClient;
import com.application.getgoproject.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentMethodActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_MOMO = 1001;
    private UserService userService;

    private Button btnPrevious, btnPay;
    private TextView tvNameCustomer, tvPhoneCustomer, tvTitlePackage, tvDetailPackage;
    private RadioGroup btnRadioGroup;
    private ArrayList<ListItem> arrayPayment;
    private String uniqueContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        UserAuthentication userAuthentication = SharedPrefManager.getInstance(this).getUser();
        String username = userAuthentication.getUsername();
        String userToken = "Bearer " + userAuthentication.getAccessToken();

        mapping();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance(this);
        userService = retrofit.create(UserService.class);

        getUserByUsername(username, userToken, new UserCallback() {
            @Override
            public void onUserFetched(User user) {
                if (user != null) {
                    tvNameCustomer.setText(user.getUserName());
                    if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
                        tvPhoneCustomer.setText(user.getPhoneNumber());
                    }
                    else {
                        tvPhoneCustomer.setText("");
                    }
                }
            }
        });

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
                if (packages != null) {
                    String priceString = packages.getPrice();
                    int amount = extractAmount(priceString);

                    String momoUrl = "momo://?action=p2p&extra=%22{\\%22dataExtract\\%22:\\%22eyJ1c2VySWQiOiIqKioqKioqNDMwIiwibmFtZSI6IlBIQU4gSElFVSBOR0hJQSIsImFtb3VudCI6NTAwMC4wLCJ0cmFuc2ZlclNvdXJjZSI6InRyYW5zZmVyX3ZpYV9saW5rIiwibGlua0lkIjoiNXhlNzlNTXB4UHB3YjdyIiwiYWdlbnRJZCI6NDAxNTg3NjksInJlY2VpdmVyVHlwZSI6IjE0IiwibWVzc2FnZSI6IlRoYW5oIHRvw6FuIGfDs2kgbmfDoHkgR2V0R28gUHJlbWl1bSIsImVuYWJsZUVkaXRBbW91bnQiOmZhbHNlfQ==\\%22}%22&url=https://momo.vn/download&amp;serviceCode=transfer_p2p&amp;refId=TransferInputMoney";

                    if (amount == 5000) {
                        momoUrl = "momo://?action=p2p&extra=%22{\\%22dataExtract\\%22:\\%22eyJ1c2VySWQiOiIqKioqKioqNDMwIiwibmFtZSI6IlBIQU4gSElFVSBOR0hJQSIsImFtb3VudCI6NTAwMC4wLCJ0cmFuc2ZlclNvdXJjZSI6InRyYW5zZmVyX3ZpYV9saW5rIiwibGlua0lkIjoiNXhlNzlNTXB4UHB3YjdyIiwiYWdlbnRJZCI6NDAxNTg3NjksInJlY2VpdmVyVHlwZSI6IjE0IiwibWVzc2FnZSI6IlRoYW5oIHRvw6FuIGfDs2kgbmfDoHkgR2V0R28gUHJlbWl1bSIsImVuYWJsZUVkaXRBbW91bnQiOmZhbHNlfQ==\\%22}%22&url=https://momo.vn/download&amp;serviceCode=transfer_p2p&amp;refId=TransferInputMoney";
                    }
                    else if (amount == 30000) {
                        momoUrl = "momo://?action=p2p&extra=%22{\\%22dataExtract\\%22:\\%22eyJ1c2VySWQiOiIqKioqKioqNDMwIiwibmFtZSI6IlBIQU4gSElFVSBOR0hJQSIsImFtb3VudCI6MzAwMDAuMCwidHJhbnNmZXJTb3VyY2UiOiJ0cmFuc2Zlcl92aWFfbGluayIsImxpbmtJZCI6Inc5YUE2cnJNejdEMWJ2TSIsImFnZW50SWQiOjQwMTU4NzY5LCJyZWNlaXZlclR5cGUiOiIxNCIsIm1lc3NhZ2UiOiJUaGFuaCB0b8OhbiBnw7NpIHRow6FuZyBHZXRHbyBQcmVtaXVtIiwiZW5hYmxlRWRpdEFtb3VudCI6ZmFsc2V9\\%22}%22&url=https://momo.vn/download&amp;serviceCode=transfer_p2p&amp;refId=TransferInputMoney";
                    } else if (amount == 250000) {
                        momoUrl = "momo://?action=p2p&extra=%22{\\%22dataExtract\\%22:\\%22eyJ1c2VySWQiOiIqKioqKioqNDMwIiwibmFtZSI6IlBIQU4gSElFVSBOR0hJQSIsImFtb3VudCI6MjUwMDAwLjAsInRyYW5zZmVyU291cmNlIjoidHJhbnNmZXJfdmlhX2xpbmsiLCJsaW5rSWQiOiJNWWVyMnBwbXJ6eGtlT0IiLCJhZ2VudElkIjo0MDE1ODc2OSwicmVjZWl2ZXJUeXBlIjoiMTQiLCJtZXNzYWdlIjoiVGhhbmggdG/DoW4gZ8OzaSBuxINtIEdldEdvIFByZW1pdW0iLCJlbmFibGVFZGl0QW1vdW50IjpmYWxzZX0=\\%22}%22&url=https://momo.vn/download&amp;serviceCode=transfer_p2p&amp;refId=TransferInputMoney";
                    }

                    if (isPackageInstalled("com.mservice.momotransfer")) {
                        Intent momoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(momoUrl));
                        startActivityForResult(momoIntent, REQUEST_CODE_MOMO);
                    } else {
                        Toast.makeText(PaymentMethodActivity.this, "Bạn chưa cài đặt ứng dụng Momo", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private int extractAmount(String priceString) {
        String cleanedString = priceString.replaceAll("[^\\d]", "");
        return Integer.parseInt(cleanedString);
    }

    private boolean isPackageInstalled(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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
//        arrayPayment.add(new ListItem(R.drawable.vnpay,"VN Pay",""));
        arrayPayment.add(new ListItem(R.drawable.momo,"Momo",""));
//        arrayPayment.add(new ListItem(R.drawable.add_card,"Card",""));
    }

    public void getUserByUsername(String username, String token, UserCallback callback) {
        try {
            Call<User> call = userService.getUserByUsername(username, token);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();
                        callback.onUserFetched(user);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    // Handle failure
                }
            });
        }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_MOMO) {
            Intent intent = new Intent(PaymentMethodActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
