package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class FirebaseCommunicator {
    enum WhichRecyclerView {
        searchRecyclerView, sellRecyclerView, ongoingRecyclerView, doneRecyclerView, none
    }

    //Firebase 로그인 계정
    private FirebaseUser user = null;
    //계정의 이름_UID로 이루어진 string
    private String userPath;
    //DB root/userPath 의 Reference
    private DatabaseReference myRef;
    private DatabaseReference tradeRef;
    private Vector tradeListVector;
    private Vector sellListVector;
    private Vector buyListVector;
    //RecyclerView 설정
    private RecyclerView recyclerView;
    private Context context;
    private Activity activity;
    private WhichRecyclerView whichRecyclerView;

    public FirebaseCommunicator(WhichRecyclerView whichRecyclerView) {
        this.whichRecyclerView = whichRecyclerView;
        while (user == null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
        }
        //경로 변수 값 설정
        userPath = user.getDisplayName() + "_" + user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("customer").child(userPath);
        tradeRef = FirebaseDatabase.getInstance().getReference().child("trade");

        ValueEventListener tradeEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.child("book").getValue(Book.class);
                Trade trade = dataSnapshot.getValue(Trade.class);
                trade.setBook(book);
                tradeListVector.add(trade);
                Log.d("DEBUG!", trade.getBook().getTitle());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Vector 초기화
        tradeListVector = new Vector<Trade>();

        //어느 RecyclerView에 사용할지에 따라 초기화 실행
        //sellListVector 초기화
        if (whichRecyclerView == WhichRecyclerView.sellRecyclerView || whichRecyclerView == WhichRecyclerView.ongoingRecyclerView || whichRecyclerView == WhichRecyclerView.doneRecyclerView) {
            sellListVector = new Vector<String>();
            ValueEventListener sellListListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String str = (String) postSnapshot.getValue();
                        sellListVector.addElement(str);
                        Log.d("DEBUG!", "addElement: " + str + ",\tnowLen: " + new Integer(sellListVector.size()).toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            myRef.child("sellList").addListenerForSingleValueEvent(sellListListener);
            //myRef.child("sellList").removeEventListener(sellListListener);

        }
        //buyListVector 초기화
        if (whichRecyclerView == WhichRecyclerView.ongoingRecyclerView || whichRecyclerView == WhichRecyclerView.doneRecyclerView) {
            buyListVector = new Vector<String>();
            ValueEventListener buyListListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String str = (String) postSnapshot.getValue();
                        buyListVector.addElement(str);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            myRef.child("buyList").addListenerForSingleValueEvent(buyListListener);
            myRef.child("buyList").removeEventListener(buyListListener);
        }



        int len = sellListVector.size();
        Log.d("DEBUG!", new Integer(len).toString());
        for(int i = 0; i < len; i++){
            tradeRef.child((String)sellListVector.get(i)).addListenerForSingleValueEvent(tradeEventListener);
            tradeRef.child((String)sellListVector.get(i)).removeEventListener(tradeEventListener);
        }


    }

    protected FirebaseCommunicator(Parcel in) {
        user = in.readParcelable(FirebaseUser.class.getClassLoader());
        userPath = in.readString();
    }

    public String getUserPath() {
        return userPath;
    }

    //RecyclerView 세팅 함수 (어떤 recyclerView인지 넘겨받아 해당 Vector를 adapter에 등록)
    public void setRecyclerView(final Context con, Activity act, RecyclerView recView, final WhichRecyclerView whichRecyclerView) {
        this.context = con;
        this.activity = act;
        this.recyclerView = recView;
        this.whichRecyclerView = whichRecyclerView;
        sellListVector = new Vector<String>();
        tradeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Book book = dataSnapshot.child("book").getValue(Book.class);
                Trade trade = dataSnapshot.getValue(Trade.class);
                trade.setBook(book);
                if (whichRecyclerView == WhichRecyclerView.sellRecyclerView) {
                    if (recyclerView != null)
                        recyclerView.setAdapter(new RecyclerViewTradeAdapter(context, tradeListVector));
                } else if (whichRecyclerView == WhichRecyclerView.ongoingRecyclerView || whichRecyclerView == WhichRecyclerView.ongoingRecyclerView) {

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    //Trade 객체 업로드 함수
    public void uploadTrade(final Trade trade) {
        //root/trade 밑에 push할 때의 key 값 얻음
        String key = tradeRef.push().getKey();
        //Upload할 trade 객체의 tradeId에 key 값 입력
        trade.setTradeId(key);
        //trade의 book 객체 Map으로 변환
        Map<String, Object> bookMap = new HashMap<>();
        trade.getBook().toMap(bookMap);
        //trade 객체 Map으로 변환
        Map<String, Object> tradeMap = new HashMap<>();
        trade.toMap(tradeMap);
        //book Upload
        tradeRef.child(key).child("book").updateChildren(bookMap);
        //trade Upload
        tradeRef.child(key).updateChildren(tradeMap);

        //현재 로그인한 사용자의 sellList Update 위해 기존의 sellListVector에 현재 trade의 key 값 추가
        sellListVector.add(key);
        tradeListVector.add(trade);
        //Update된 ArrayList를 FIrebase에 Upload
        myRef.child("sellList").setValue(sellListVector);
        return;
    }

    //CustomerId로 Customer 객체를 Return하는 함수
    public Customer getCustomer(String customerId) {
        final Customer[] customer = {new Customer()};
        FirebaseDatabase.getInstance().getReference().child("customer").child(customerId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        customer[0] = dataSnapshot.getValue(Customer.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        return customer[0];
    }

}
