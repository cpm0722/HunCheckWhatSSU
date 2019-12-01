package ssu.ssu.huncheckwhatssu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SelectPurchaserActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "HUNYC";

    String tradeKey;
    String sellerId;
    FirebaseHelper firebaseHelper;
    RecyclerView recyclerView;
    SelectPurchaserAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Vector<Customer> purchasers;
    Vector<Boolean> isClicked;
    Button saveBtn;
    Button cancelBtn;

    Customer selectedPurchaser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_purchaser);

        //버튼 초기화
        saveBtn = findViewById(R.id.select_purchaser_btn);
        cancelBtn = findViewById(R.id.select_purchaser_cancel_btn);
        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        //게시물 키 받기
        Intent intent = getIntent();
        tradeKey = intent.getStringExtra("tradeKey");
        sellerId = intent.getStringExtra("sellerId");
        purchasers = new Vector<>();
        isClicked = new Vector<>();

        firebaseHelper = new FirebaseHelper();
        firebaseHelper.addCallBackListener(new FirebaseHelper.CallBackListener() {
            @Override
            public void afterGetCustomer(Customer customer) {
                purchasers.add(customer);
                isClicked.add(false);
                Log.d(TAG, customer.getName());
                adapter.notifyDataSetChanged();
            }
        });
        //게시물에 요청한 사람 정보 가져옴
        firebaseHelper.getPurchaseRequest(tradeKey);

        recyclerView = findViewById(R.id.select_purchaser_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SelectPurchaserAdapter(this, purchasers, isClicked);

        //아이템 클릭 리스너 달기
        adapter.setOnItemClickListener(new SelectPurchaserAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, int position) {
                if (isClicked.get(position) == true) {
                    selectedPurchaser = null;
                    int size = isClicked.size();
                    for (int i = 0; i < size; i++)
                        isClicked.set(i, false);
                } else {
                    int size = isClicked.size();
                    for (int i = 0; i < size; i++)
                        isClicked.set(i, false);
                    isClicked.set(position, true);
                    selectedPurchaser = purchasers.get(position);
                }
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view == saveBtn) {
            if (selectedPurchaser != null) {
//                Intent intent = new Intent();
//                intent.putExtra("purchaserId", selectedPurchaser.getId());   //구매 확정자 id 넘겨줌
//                setResult(RESULT_OK, intent);
                firebaseHelper.updatePurchaser(tradeKey, selectedPurchaser.getId(), sellerId); //서버 DB 작업
                Toast.makeText(this, "선택 완료, 진행중인 거래를 확인하세요.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "선택된 구매자가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (view == cancelBtn) {
//            setResult(RESULT_CANCELED);
            Toast.makeText(this, "취소하셨습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
