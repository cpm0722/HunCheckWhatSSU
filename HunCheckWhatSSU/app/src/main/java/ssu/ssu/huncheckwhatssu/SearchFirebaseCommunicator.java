package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Spinner;

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

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchFirebaseCommunicator {
    //Firebase Database의 주소
    private DatabaseReference mPostReference;
    //Firebase 로그인 계정
    private FirebaseUser user = null;
    //계정의 이름_UID로 이루어진 string (DB root/userPath로 사용)
    private String userPath;
    //DB root/userPath 의 Reference
    private DatabaseReference myRef;
    private List<Trade> list;
    //RecyclerView 설정
    RecyclerView recyclerView;
    private Context context;
    private Activity activity;

    public SearchFirebaseCommunicator(String path, Context context, Activity activity, RecyclerView recyclerView){
        mPostReference = FirebaseDatabase.getInstance().getReference();

        while (user == null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
        }

        userPath = path;
        myRef = mPostReference.child(userPath);
        list = new ArrayList<>();

        this.context = context;
        this.activity = activity;
        this.recyclerView = recyclerView;

        setRecyclerView();
    }

    //RecyclerView 및 Context, Activity 받아옴 (Activity 및 Fragment 전환 시마다 호출)
    public void setRecyclerView(){
        RecyclerViewTradeAdapter_Search adapter = new RecyclerViewTradeAdapter_Search(context, this.getList());

        recyclerView.setAdapter(adapter);
    }

    public List<Trade> getList() {
        return list;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }
}
