package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class FirebaseCommunicator {
    //Firebase Database의 주소
    private DatabaseReference mPostReference;
    //Firebase 로그인 계정
    private FirebaseUser user = null;
    //계정의 이름_UID로 이루어진 string (DB root/userPath로 사용)
    private String userPath;
    //DB root/userPath 의 Reference
    private DatabaseReference myRef;
    private ValueEventListener valueEventListener;
    private List list;
    //RecyclerView 설정
    private RecyclerView recyclerView;
    private Context context;
    private Activity activity;

    public FirebaseCommunicator(){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        while (user == null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
        }
        userPath = user.getDisplayName() + "_" + user.getUid();
        myRef = mPostReference.child(userPath);

        //
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Trade>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Book book = postSnapshot.getValue(Book.class);
                    Customer customer = postSnapshot.getValue(Customer.class);
                    Trade nowObject = new Trade(book, customer);
                    list.add(nowObject);
                    if(recyclerView != null)
                        recyclerView.setAdapter(new RecyclerViewTradeAdapter(context, list));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(valueEventListener);

    }

    //RecyclerView 및 Context, Activity 받아옴 (Activity 및 Fragment 전환 시마다 호출)
    public void setRecyclerView(Context context, Activity activity, RecyclerView recyclerView){
        this.context = context;
        this.activity = activity;
        this.recyclerView = recyclerView;
        return;
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

    public void uploadTrade(Trade trade) {
        String key = myRef.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        trade.getBook().toMap(childUpdates);
        trade.getSeller().toMap(childUpdates);
        myRef.child(key).updateChildren(childUpdates);
        return;
    }

}