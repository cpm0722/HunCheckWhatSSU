package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class FirebaseCommunicator {
    DatabaseReference mPostReference;
    private FirebaseAuth firebaseAuth = null;
    private FirebaseUser user = null;
    private String path;
    private DatabaseReference myRef;
    private ChildEventListener childEventListener;
    private List list;
    private RecyclerView recyclerView;
    final private Context context;
    final private Activity activity;

    public FirebaseCommunicator(final Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        mPostReference = FirebaseDatabase.getInstance().getReference();
        while (user == null) {
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
        }
        path = user.getDisplayName() + "_" + user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(path);

        childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trade trade = dataSnapshot.getValue(Trade.class);
                Log.d("DEBUG!", trade.getBook().getName() + '\t' + trade.getSeller().getName());
                Toast toast = Toast.makeText(context, trade.getBook().getName() + '\t' + trade.getSeller().getName(), Toast.LENGTH_SHORT);
                toast.show();

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
        };
        myRef.addChildEventListener(childEventListener);

    }

    public void uploadTrade(Trade trade){
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> bookValues = null;
        Map<String, Object> sellerValues = null;
        bookValues = trade.getBook().toMap();
        sellerValues = trade.getSeller().toMap();
        mPostReference.child("users").child(path).child("book").push().setValue(bookValues);
        mPostReference.child("users").child(path).child("seller").push().setValue(sellerValues);
        return;
    }

}
