package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.os.CpuUsageInfo;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class FirebaseCommunicator {
    DatabaseReference mPostReference;
    private FirebaseAuth firebaseAuth = null;
    private FirebaseUser user = null;
    private String userPath;
    private ValueEventListener valueEventListener;
    private List list;
    private RecyclerView recyclerView;
    final private Context context;
    final private Activity activity;

    public FirebaseCommunicator(final Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        mPostReference = FirebaseDatabase.getInstance().getReference();
        while (user == null) {
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
        }
        userPath = user.getDisplayName() + "_" + user.getUid();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Trade>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Book book = postSnapshot.getValue(Book.class);
                    Customer customer = postSnapshot.getValue(Customer.class);
                    Trade nowObject = new Trade(book, customer);
                    Log.d("DEBUG!", nowObject.getBook().getTitle());
                    list.add(nowObject);
                    if(recyclerView != null) {
                        recyclerView.setAdapter(new RecyclerViewTradeAdapter(context, list));
                        Log.d("DEBUG!", "ADAPTER!");
                    }
                    else
                        Log.d("DEBUG!", "VIEW NULL!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mPostReference.addValueEventListener(valueEventListener);
        //mPostReference.addChildEventListener(childEventListener);

    }

    public void setRecyclerView(RecyclerView recyclerView){
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
        Map<String, Object> childUpdates = new HashMap<>();
        trade.getBook().toMap(childUpdates);
        trade.getSeller().toMap(childUpdates);
        String path = userPath + timeToString();
        mPostReference.child(path).setValue(childUpdates);
        mPostReference.addValueEventListener(valueEventListener);
        return;
    }

}
