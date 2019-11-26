package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SelectPurchaserActivity extends AppCompatActivity {

    public static String TAG = "YECHANTT";

    String tradeKey;
    FirebaseHelper firebaseHelper;
    Vector<Customer> purchasers;
    RecyclerView recyclerView;
    SelectPurchaserAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_purchaser);
        Intent intent = getIntent();
        tradeKey = intent.getStringExtra("tradeKey");

        purchasers = new Vector<>();

        firebaseHelper = new FirebaseHelper();
        firebaseHelper.addCallBackListener(new FirebaseHelper.CallBackListener() {
            @Override
            public void afterGetCustomer(Customer customer) {
                purchasers.add(customer);
                Log.d(TAG,customer.getName());
                int size = purchasers.size()-1;
                adapter.notifyDataSetChanged();
            }
        });
        firebaseHelper.getPurchaseRequest(tradeKey);

        recyclerView = findViewById(R.id.select_purchaser_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SelectPurchaserAdapter(this,purchasers);

        adapter.setOnItemClickListener(new SelectPurchaserAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }
}
