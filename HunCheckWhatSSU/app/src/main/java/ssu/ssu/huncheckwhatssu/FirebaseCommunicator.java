package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class FirebaseCommunicator {
    enum WhichRecyclerView {
        searchRecyclerView, sellRecyclerView, ongoingRecyclerView, doneRecyclerView, none
    }

    private static String TAG = "DEBUG!";

    //Firebase 로그인 계정
    private Customer me;
    private static FirebaseUser user = null;
    //계정의 이름_UID로 이루어진 string
    private static String userPath = null;
    //DB root/userPath 의 Reference
    private DatabaseReference myRef = null;
    private static DatabaseReference tradeRef = null;
    private Vector<Trade> sellTradeListVector = null;
    private Vector<Trade> ongoingTradeListVector = null;
    private Vector<Trade> doneTradeListVector = null;
    private Vector<String> sellListVector = null;
    private Vector<String> tradeFragmentTradeIdListVector = null;
    //RecyclerView 설정
    private RecyclerView recyclerView;
    private Context context;
    private Activity activity;
    private WhichRecyclerView whichRecyclerView;

    private boolean isEvaluation = false;

    public FirebaseCommunicator() {
    }

    public FirebaseCommunicator(final String id, Context context, Activity activity, RecyclerView recyclerView) {
        isEvaluation = true;
        this.whichRecyclerView = WhichRecyclerView.doneRecyclerView;
        setRecyclerView(context, activity, recyclerView, this.whichRecyclerView);
        //  user 값 받아옴
        userPath = id;
        //  현재 유저의 폴더 Firebase에서의 Reference
        if (myRef == null)
            myRef = FirebaseDatabase.getInstance().getReference().child("customer").child(userPath);
        //  root/trade의 Reference
        if (tradeRef == null)
            tradeRef = FirebaseDatabase.getInstance().getReference().child("trade");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = new Customer(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Length 변수 초기화

        //  Vector 초기화
        if (doneTradeListVector == null)
            doneTradeListVector = new Vector<Trade>();

    //  현재 User가 purchaser로 등록되어 있는 trade들의 key 값을 Vector로 저장
        tradeFragmentTradeIdListVector = new Vector<String>();
        doneTradeListVector = new Vector<Trade>();
        //  myRef/tradeList 에 등록할 Event Listener(tradeList에 있는 trade의 key값들을 받아와 ongoing/doneTradeListVector에 저장)
        Log.d(TAG, "FirebaseCommunicator 123 : " + myRef.toString());

        myRef.child("tradeList").addListenerForSingleValueEvent(new TradeListListener());
    }

    //  FirebaseCommunicator 생성자, 초기화 실행
    public FirebaseCommunicator(final WhichRecyclerView whichRecyclerView) {
        this.whichRecyclerView = whichRecyclerView;
        //  user 값 받아옴
        if (user == null)
            user = FirebaseAuth.getInstance().getCurrentUser();
        //  root/customer 하위 user 고유 폴더 명 (user의 UID)
        if (userPath == null)
            userPath = user.getDisplayName() + "_" + user.getUid();
        //  현재 유저의 폴더 Firebase에서의 Reference
        if (myRef == null)
            myRef = FirebaseDatabase.getInstance().getReference().child("customer").child(userPath);
        //  root/trade의 Reference
        if (tradeRef == null)
            tradeRef = FirebaseDatabase.getInstance().getReference().child("trade");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = new Customer(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Length 변수 초기화

        //  Vector 초기화
        if (sellTradeListVector == null)
            sellTradeListVector = new Vector<Trade>();
        if (ongoingTradeListVector == null)
            ongoingTradeListVector = new Vector<Trade>();
        if (doneTradeListVector == null)
            doneTradeListVector = new Vector<Trade>();
        //  어느 RecyclerView에 사용할지에 따라 초기화 실행
        if (whichRecyclerView == WhichRecyclerView.sellRecyclerView) {
            //  sellListVector 초기화
            //  현재 User가 seller로 등록되어 있는 trade들의 key 값을 Vector로 저장
            sellListVector = new Vector<String>();
            //  myRef/sellList 에 등록할 Event Listener(sellList에 있는 trade의 key값들을 받아와 sellListVector에 저장)
            myRef.child("sellList").addListenerForSingleValueEvent(new SellListListener());
        }
        //tradeFragmentTradeIdListVector 초기화
        else if (whichRecyclerView == WhichRecyclerView.ongoingRecyclerView || whichRecyclerView == WhichRecyclerView.doneRecyclerView) {
            //  현재 User가 purchaser로 등록되어 있는 trade들의 key 값을 Vector로 저장
            tradeFragmentTradeIdListVector = new Vector<String>();
            ongoingTradeListVector = new Vector<Trade>();
            doneTradeListVector = new Vector<Trade>();
            //  myRef/tradeList 에 등록할 Event Listener(tradeList에 있는 trade의 key값들을 받아와 ongoing/doneTradeListVector에 저장)
            myRef.child("tradeList").addListenerForSingleValueEvent(new TradeListListener());
        }
    }

    protected FirebaseCommunicator(Parcel in) {
        user = in.readParcelable(FirebaseUser.class.getClassLoader());
        userPath = in.readString();
    }

    public FirebaseUser getUser() {
        return user;
    }

    public String getUserPath() {
        return userPath;
    }

    public Vector<Trade> getSellTradeListVector() {
        return sellTradeListVector;
    }

    public Vector<Trade> getOngoingTradeListVector() {
        return ongoingTradeListVector;
    }

    public Vector<Trade> getDoneTradeListVector() {
        return doneTradeListVector;
    }

    public static String getMyId(){
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "_" + FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Customer getMe() {
        return me;
    }

    //  RecyclerView 세팅 함수 (어떤 recyclerView인지 넘겨받아 해당 Vector를 adapter에 등록)
    public void setRecyclerView(final Context context, Activity activity, RecyclerView recyclerView,
                                final WhichRecyclerView whichRecyclerView) {
        //  초기화
        this.context = context;
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.whichRecyclerView = whichRecyclerView;
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
        //book 객체의 정보 Frirebase에 Upload
        tradeRef.child(key).child("book").updateChildren(bookMap);
        //trade 객체의 정보 FIrebase에 Upload
        tradeRef.child(key).updateChildren(tradeMap);

        //현재 로그인한 사용자의 sellList Update 위해 기존의 sellListVector에 현재 trade의 key 값 추가
        sellListVector.add(key);
        sellTradeListVector.add(trade);
        //Update된 sellListVector를 FIrebase에 Upload
        myRef.child("sellList").child(key).setValue(key);
        //내 정보 업데이트
        me.setTradeCount(me.getTradeCount() + 1);
        myRef.child("tradeCount").setValue(me.getTradeCount());
        return;
    }

    //수정된 trade객체를 받아 Firebasd에 등록 (trade 객체의 tradeId 사용)
    public static void editTrade(Trade trade) {
        //trade의 book 객체 Map으로 변환
        Map<String, Object> bookMap = new HashMap<>();
        trade.getBook().toMap(bookMap);
        //trade 객체 Map으로 변환
        Map<String, Object> tradeMap = new HashMap<>();
        trade.toMap(tradeMap);
        //TradeId 획득
        String tradeId = trade.getTradeId();
        //book 객체의 정보 Frirebase에 Upload
        FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("book").updateChildren(bookMap);
        FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).updateChildren(tradeMap);
    }

    //trade객체를 Firebase에서 삭제하는 함수 (SellFragment에서 사용)
    public static void deleteTrade(Trade trade) {
        FirebaseDatabase.getInstance().getReference().child("customer").child(trade.getSellerId()).child("sellList").child(trade.getTradeId()).removeValue();
        tradeRef.child(trade.getTradeId()).removeValue();
        return;
    }

    //거래 예약 잡힌 경우
    public static void tradePrecontract(String tradeId, String sellerId, String purchaserId) {
        //판매자의 sellLIst에서 삭제
        FirebaseDatabase.getInstance().getReference().child("customer").child(sellerId).child("sellList").child(tradeId).setValue(null);
        //판매자의 tradeLIst에 추가
        FirebaseDatabase.getInstance().getReference().child("customer").child(sellerId).child("tradeList").child(tradeId).setValue(tradeId);
        //구매자의 tradeLIst에 추가
        FirebaseDatabase.getInstance().getReference().child("customer").child(purchaserId).child("tradeList").child(tradeId).setValue(tradeId);
        //TradeState PRECONTRACT로 변경
        FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("tradeState").setValue("PRECONTRACT");
        //purchaserId 변경
        FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("purchaserId").setValue(purchaserId);
        return;
    }

    //거래 파기된 경우
    public void tradeCancel(String tradeId, String sellerId, String purchaserId) {
        me.setCreditRating(me.getCreditRating() - 0.5);
        FirebaseDatabase.getInstance().getReference().child("customer").child(getMyId()).child("CreditRating").setValue(me.getCreditRating());
        me.setCancelCount(me.getCancelCount() + 1);
        FirebaseDatabase.getInstance().getReference().child("customer").child(getMyId()).child("cancelCount").setValue(me.getCancelCount());
        //판매자의 tradeLIst에서 삭제
        FirebaseDatabase.getInstance().getReference().child("customer").child(sellerId).child("tradeList").child(tradeId).setValue(null);
        //구매자의 tradeLIst에서 삭제
        FirebaseDatabase.getInstance().getReference().child("customer").child(purchaserId).child("tradeList").child(tradeId).setValue(null);
        //판매자의 sellLIst에 추가
        FirebaseDatabase.getInstance().getReference().child("customer").child(sellerId).child("sellList").child(tradeId).setValue(tradeId);
        //TradeState WAIT로 변경
        FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("tradeState").setValue("WAIT");
        //purchaserId null로 변경
        FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("purchaserId").setValue(null);
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

    //tradeRef 탐색하면서 trade 객체의 정보 받아와 Vector<Trade>에 trade 객체 추가하는 Event Listener
    public class TradeListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Book book = dataSnapshot.child("book").getValue(Book.class);
            final Trade trade = dataSnapshot.getValue(Trade.class);
            trade.setSeller(new Customer(trade.getSellerId()));
            trade.getSeller().setCustomerDataFromUID(null);
            if(trade.getPurchaserId() != null){
                trade.setPurchaser(new Customer(trade.getPurchaserId()));
                trade.getPurchaser().setCustomerDataFromUID(null);
            }
            trade.setBook(book);
            switch (whichRecyclerView) {
                case sellRecyclerView:
                    sellTradeListVector.add(trade);
                    break;
                case ongoingRecyclerView:
                case doneRecyclerView:
                    if (trade.getTradeState() == Trade.TradeState.COMPLETE) {
                        Log.d(TAG, "onDataChange: " + trade.toString());
                        doneTradeListVector.add(trade);
                    }
                    else {
                        ongoingTradeListVector.add(trade);
                    }
                    break;
            }
            if (recyclerView != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    //tradeRef 탐색하면서 trade 객체의 정보 받아와 Vector<Trade>에 trade 객체 추가하는 Event Listener
    public class EvaluationTradeListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Book book = dataSnapshot.child("book").getValue(Book.class);
            final Trade trade = dataSnapshot.getValue(Trade.class);

            Log.d(TAG, "onDataChange: evaluationListener123" + dataSnapshot.toString());

            trade.setSeller(new Customer(trade.getSellerId()));
            trade.getSeller().setCustomerDataFromUID(null);
            if(trade.getPurchaserId() != null){
                Log.d(TAG, "onDataChange: ");
                trade.setPurchaser(new Customer(trade.getPurchaserId()));
                trade.getPurchaser().setCustomerDataFromUID(null);
            }
            trade.setBook(book);
            Log.d(TAG, "onDataChange: evaluationListener" + trade.toString());
            Log.d(TAG, "onDataChange: userPath" + userPath);
            if(trade.getSellerId().equals(userPath)){
                Log.d(TAG, "onDataChange: isSeller!" + trade.getSellerId());
             if(trade.getSellerRate() != -1){
                 Log.d(TAG, "onDataChange: isSellerRate not -1!" + trade.getSellerId());
                 doneTradeListVector.add(trade);
             }
            }
            else if(trade.getPurchaserId().equals(userPath)){
                Log.d(TAG, "onDataChange: isPurchaser!" + trade.getPurchaserId());
                if(trade.getPurchaserRate() != -1){
                    Log.d(TAG, "onDataChange: isPurchaserRate not -1!" + trade.getPurchaserId());
                    doneTradeListVector.add(trade);
                }
            }
            if (recyclerView != null) {
                Log.d(TAG, "onDataChange: " + recyclerView.getAdapter().toString());
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }


    public class SellListListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String str = (String) postSnapshot.getValue();
                //  얻어온 trade key를 sellListVector에 추가
                sellListVector.addElement(str);
                //  root/trade/key값 경로에 tradeEventListener 추가
                tradeRef.child(str).addListenerForSingleValueEvent(new TradeListener());
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    public class TradeListListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String str = (String) postSnapshot.getValue();
                //  얻어온 trade key를 tradeFragmentTradeIdListVector에 추가
                tradeFragmentTradeIdListVector.addElement(str);
                Log.d(TAG, "onDataChange: tradeListListener" + str);
                //  root/trade/key값 경로에 tradeEventListener 추가
                if(isEvaluation)
                    tradeRef.child(str).addListenerForSingleValueEvent(new EvaluationTradeListener());
                else tradeRef.child(str).addListenerForSingleValueEvent(new TradeListener());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
