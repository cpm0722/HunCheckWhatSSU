package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SelectPurchaserActivity extends AppCompatActivity {
    String tradeKey;
    FirebaseHelper firebaseHelper;
    Vector<Customer> purchasers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_purchaser);
        Intent intent = getIntent();
        tradeKey = intent.getStringExtra("tradeKey");
        firebaseHelper = new FirebaseHelper();
        firebaseHelper.addCallBackListener(new FirebaseHelper.CallBackListener() {
            @Override
            public void afterGetCustomer(Customer customer) {
                purchasers.add(customer);
            }
        });
    }
}
