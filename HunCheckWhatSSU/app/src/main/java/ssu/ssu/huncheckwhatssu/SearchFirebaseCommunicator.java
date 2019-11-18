package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class SearchFirebaseCommunicator {
    //Firebase Database의 주소
    private DatabaseReference mPostReference;
    //Firebase 로그인 계정
    private FirebaseUser user = null;
    //계정의 이름_UID로 이루어진 string (DB root/userPath로 사용)
    private String userPath;
    //DB root/userPath 의 Reference
    private DatabaseReference myRef;
    private ValueEventListener valueEventListener;
    private List<Trade> list;
    //RecyclerView 설정
    private RecyclerView recyclerView;
    private Context context;
    private Activity activity;

    public SearchFirebaseCommunicator(String path){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        while (user == null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
        }
        userPath = path;
        myRef = mPostReference.child(userPath);


        // 첫 로드시 100개 데이터 로드로 제한
        myRef.limitToLast(100);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null && dataSnapshot.exists()) {
                    for (DataSnapshot tradeSnap : dataSnapshot.getChildren()) {
//                        Log.d("JS", "onDataChange: " + tradeSnap.getValue().toString());
                        Trade trade = tradeSnap.getValue(Trade.class);
//                        Map<String, Object> childUpdates = (Map<String, Object>) tradeSnap.getValue();
//                        trade.getSeller().setCustomerDataFromUID();
                        list.add(trade);
//                        Log.d("js", "onDataChange: " + trade.toString());
//                        Book book = (Book) childUpdates.get("book");
                        Log.d("JS", "onDataChange: " + trade.toString());
                    }

                    if (recyclerView != null)
                        recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //RecyclerView 및 Context, Activity 받아옴 (Activity 및 Fragment 전환 시마다 호출)
    public void setRecyclerView(Context context, Activity activity, RecyclerView recyclerView){
        this.context = context;
        this.activity = activity;
        this.recyclerView = recyclerView;

        RecyclerViewTradeAdapter_Search adapter = new RecyclerViewTradeAdapter_Search(context, list);
        adapter.setOnRefreshListener(new RecyclerViewTradeAdapter_Search.custom_RefreshListener() {
            @Override
            public void onRefreshListener() {

//                Log.d("js", "onRefreshListener");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null && dataSnapshot.exists()) {
                            list.clear();
                            for (DataSnapshot tradeSnap : dataSnapshot.getChildren()) {
                                Log.d("JS", "onDataChange: " + tradeSnap.getValue().toString());
                                Trade trade = tradeSnap.getValue(Trade.class);
//                                Map<String, Object> childUpdates = (Map<String, Object>) tradeSnap.getValue(true);
//                                trade.getSeller().setCustomerDataFromUID();
                                list.add(trade);
//                                Log.d("js", "onDataChange: " + childUpdates.toString());
//                                Log.d("js", "onDataChange: " + childUpdates.get(""));
                            }

                            if (getRecyclerView() != null)
                                getRecyclerView().getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public String timeToString() {
        Calendar c = Calendar.getInstance();
        String str;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        str = String.valueOf(year);
        if (month < 10)
            str = str + "-0" + String.valueOf(month);
        else
            str = str + "-" + String.valueOf(month);

        if (day < 10)
            str = str + "-0" + String.valueOf(day);
        else
            str = str + "-" + String.valueOf(day);

        if (hour < 10)
            str = str + "-0" + String.valueOf(hour);
        else
            str = str + "-" + String.valueOf(hour);

        if (minute < 10)
            str = str + ":0" + String.valueOf(minute);
        else
            str = str + ":" + String.valueOf(minute);

        if (second < 10)
            str = str + ":0" + String.valueOf(second);
        else
            str = str + ":" + String.valueOf(second);

        return str;
    }

    public List<Trade> getList() {
        return list;
    }

    public void setList(List<Trade> list) {
        this.list = list;
    }

    public void uploadTrade(Trade trade) {
        String key = myRef.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        trade.toMap(childUpdates);
        myRef.child(key).updateChildren(childUpdates);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
