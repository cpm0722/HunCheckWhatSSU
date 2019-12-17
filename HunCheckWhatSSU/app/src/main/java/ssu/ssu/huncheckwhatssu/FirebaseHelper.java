package ssu.ssu.huncheckwhatssu;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class FirebaseHelper {


    public DatabaseReference root;
    public DatabaseReference purchase_request;
    public DatabaseReference customer;
    public DatabaseReference trade;
    public String myUid;
    public CallBackListener callBackListener;
    public NotificationListener notificationListener;

    private ValueEventListener notifyListener;

    public FirebaseHelper() {
        this.root = FirebaseDatabase.getInstance().getReference();
        this.purchase_request = root.child("purchase_request");
        this.customer = root.child("customer");
        this.trade = root.child("trade");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myUid = firebaseUser.getDisplayName() + "_" + firebaseUser.getUid();
    }

    //Customer객체를 서버에 올리는 함수
    public void upLoadCustomer(Customer customer) {
        String id = customer.getId();
        DatabaseReference path = FirebaseDatabase.getInstance().getReference().child("customer").child(id);
        HashMap<String, Object> update = new HashMap<>();
        customer.toMap(update);
        path.updateChildren(update);
    }

    //Id로 Customer 정보를 가져오는 함수 콜백 리스너로 넘긴다.
    public void getCustomerById(String Uid) {
        DatabaseReference path = customer.child(Uid);
        path.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (callBackListener != null) {
                    callBackListener.afterGetCustomer(new Customer(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("YECHAN", "Data Read Fail");
            }
        });
    }


    //구매요청을 보내는 함수
    public void sendPurchaseRequest(String tradeKey, String Uid) {
        DatabaseReference Ref = purchase_request.child(tradeKey);
        Ref.child(Uid).setValue(Uid);
    }

    //해당 trade에 대한 구매요청한 사람들의 uid를 받는 함수
    public void getPurchaseRequest(String tradeKey) {
        DatabaseReference Ref = purchase_request.child(tradeKey);
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vector<String> purchaserUids = new Vector<>();
                for (DataSnapshot uidSnapshot : dataSnapshot.getChildren()) {
                    purchaserUids.add(uidSnapshot.getValue(String.class));
                }
                afterGetPurchaserUid(purchaserUids);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getPurchaseRequestCount(String tradeKey) {
        DatabaseReference Ref = purchase_request.child(tradeKey);
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Vector<String> purchaserUids = new Vector<>();
//                for (DataSnapshot uidSnapshot : dataSnapshot.getChildren()) {
//                    purchaserUids.add(uidSnapshot.getValue(String.class));
//                }
                callBackListener.afterGetPurchaseRequestCount((int)dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //purchaserUid를 받고 purchaser정보를 서버에서 가져옴
    public void afterGetPurchaserUid(Vector<String> purchaserUids) {
        int size = purchaserUids.size();
        for (int i = 0; i < size; i++) {
            getCustomerById(purchaserUids.get(i));
        }
    }

    //trade를 업로드하는 함수
    public void upLoadTrade(Trade trade) {
        HashMap<String, Object> update = new HashMap<>();
        DatabaseReference databaseReference = this.trade.push();
        String key = databaseReference.getKey();
        trade.setTradeId(key);
        trade.toMap(update);
        databaseReference.updateChildren(update);
        databaseReference = this.customer.child(myUid).child("sellList").child(key);
        databaseReference.setValue(key);
    }

    //선택한 purchaser을 서버에 업데이트한다.
    public void updatePurchaser(String tradeKey, String purchaserUid, String sellerUid) {

        trade.child(tradeKey).child("purchaserId").setValue(purchaserUid);
        trade.child(tradeKey).child("tradeState").setValue("PRECONTRACT");
        customer.child(sellerUid).child("sellList").child(tradeKey).removeValue();
        customer.child(sellerUid).child("tradeList").child(tradeKey).setValue(tradeKey);
        customer.child(purchaserUid).child("tradeList").child(tradeKey).setValue(tradeKey);
        deletePurchaseRequest(tradeKey);
    }
    public void deletePurchaseRequest(String tradeKey){
        purchase_request.child(tradeKey).removeValue();

    }

    public void getSellListById(String uid){
        customer.child(uid).child("sellList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vector<String> vector = new Vector<>();
                for(DataSnapshot uidSnapshot : dataSnapshot.getChildren())
                    vector.add(uidSnapshot.getValue(String.class));
                notificationListener.afterGetTradeUids(vector);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getTradeBookNameByTradeKey(String tradeKey){
        trade.child(tradeKey).child("book").child("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (notificationListener != null)
                    notificationListener.afterGetTradeBookName(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setNotifyPurchaseRequest(Vector<String> sellListKeys) {
      notifyListener = new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              String tradekey = dataSnapshot.getKey();
              if(dataSnapshot.hasChildren()){
                  getTradeBookNameByTradeKey(tradekey);
              }
          }
          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      };
        for (String key : sellListKeys) {
            purchase_request.child(key).addValueEventListener(notifyListener);
        }
    }
    public void deleteNotifyListener(Vector<String> sellListKeys){
        for(String trade : sellListKeys){
            purchase_request.child(trade).removeEventListener(notifyListener);
        }
    }

    public interface CallBackListener {
        void afterGetCustomer(Customer customer);

        void afterGetPurchaseRequestCount(int count);
    }

    public void addCallBackListener(CallBackListener testListener) {
        this.callBackListener = testListener;
    }

    public interface NotificationListener {
        void afterGetTradeUids(Vector<String> sellListUids);

        void afterGetTradeBookName(String bookName);
    }

    public void addNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }
}
