package ssu.ssu.huncheckwhatssu;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class FirebaseHelper {


    public DatabaseReference root;
    public DatabaseReference purchase_request;
    public DatabaseReference customer;
    public DatabaseReference trade;
    public String myUid;
    public CallBackListener callBackListener;

    public FirebaseHelper(){
        this.root = FirebaseDatabase.getInstance().getReference();
        this.purchase_request = root.child("purchase_request");
        this.customer = root.child("customer");
        this.trade = root.child("trade");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myUid =firebaseUser.getDisplayName()+"_"+firebaseUser.getUid();
    }

    public void upLoadCustomer(Customer customer){
        String id = customer.getId();
        DatabaseReference path = FirebaseDatabase.getInstance().getReference().child("customer").child(id);
        HashMap<String, Object> update = new HashMap<>();
        customer.toMap(update);
        path.updateChildren(update);
    }

    public void getCustomerById(String Uid){
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

    public void sendPurchaseRequest(String tradeKey,String Uid){
        DatabaseReference Ref = purchase_request.child(tradeKey);
        Ref.child(Uid).setValue(Uid);
    }
    public void getPurchaseRequest(String tradeKey){
        DatabaseReference Ref = purchase_request.child(tradeKey);
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vector<String> purchaserUids = new Vector<>();
                for(DataSnapshot uidSnapshot : dataSnapshot.getChildren()){
                    purchaserUids.add(uidSnapshot.getValue(String.class));
                }
                afterGetPurchaserUid(purchaserUids);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void afterGetPurchaserUid(Vector<String> purchaserUids){
        int size = purchaserUids.size();
        for(int i=0;i<size;i++){
            getCustomerById(purchaserUids.get(i));
        }
    }
    public void upLoadTrade(Trade trade){
        HashMap<String,Object> update = new HashMap<>();
        trade.toMap(update);
        DatabaseReference databaseReference = this.trade.push();
        String key = databaseReference.getKey();
        trade.setTradeId(key);
        databaseReference.updateChildren(update);
        databaseReference = this.customer.child(myUid).child("sellList").child(key);
        databaseReference.setValue(key);
    }

    public void updatePurchaser(String purchaserUid){

    }
    public interface CallBackListener {
        void afterGetCustomer(Customer customer);
    }
    public void addCallBackListener(CallBackListener testListener) {
        this.callBackListener = testListener;
    }
}
