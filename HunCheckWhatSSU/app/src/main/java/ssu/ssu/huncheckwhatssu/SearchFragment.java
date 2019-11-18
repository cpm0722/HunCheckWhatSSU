package ssu.ssu.huncheckwhatssu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;

import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {
    Button bookInfoBtn;
    Button addBtn;
    SearchFirebaseCommunicator firebase;
    Spinner college_spin;
    Spinner department_spin;
    Spinner subject_spin;
    SearchView searchView;
    ArrayList<DBData> collegeData;
    ArrayList<DBData> departmentData;
    ArrayList<DBData> subjectData;

    private RecyclerView recyclerView;
    private RecyclerViewTradeAdapter_Search adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        college_spin = root.findViewById(R.id.fragment_search_college_spin);
        department_spin = root.findViewById(R.id.fragment_search_department_spin);
        subject_spin = root.findViewById(R.id.fragment_search_subject_spin);
        searchView = root.findViewById(R.id.fragment_search_searchView);

        college_spin.setOnItemSelectedListener(this);
        department_spin.setOnItemSelectedListener(this);
        subject_spin.setOnItemSelectedListener(this);
        searchView.setOnQueryTextListener(this);


        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(0).setChecked(true);

        //RecyclerView
        recyclerView = root.findViewById(R.id.search_fragment_recycler_view);
        adapter = new RecyclerViewTradeAdapter_Search(this.getContext(), new ArrayList<Trade>());
        recyclerView.setAdapter(adapter);
        RecyclerViewTradeAdapter_Search.setSwipeable(this.getContext(), this.getActivity(), recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecyclerViewTradeAdapter_Search.SetRefresh((SwipeRefreshLayout)root.findViewById(R.id.swipe_fragment_search));



        //SearchFirebaseCommunicator 생성
        firebase = new SearchFirebaseCommunicator("tb_trade");
        //FirebaseCommunicator에 RecyclerView 설정
        firebase.setRecyclerView(this.getContext(), this.getActivity(), recyclerView);

//        TEST용 Firebase 추가 버튼
        addBtn = root.findViewById(R.id.fragment_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Book book = new Book("testISBN10", "testISBN13", "testTitle", "testImage", "testAuthor", 12000, 13000, "testPublisher", "testPubDate", "testDescription", "8", "38" ,"33", new BookState());
                Customer seller = new Customer("testId", "testName", "testPhoneNumber", "testAdress", (float) 1.4);
                Customer purchaser = new Customer("testId", "testName", "testPhoneNumber", "testAdress", (float) 1.4);
                Trade trade = new Trade(book, seller, purchaser, Trade.TradeState.WAIT, "장소 미정", Calendar.getInstance());
                firebase.uploadTrade(trade);
            }
        });


        // 상단 카테고리 작업
        collegeData = new ArrayList<>();
        departmentData = new ArrayList<>();
        subjectData = new ArrayList<>();

        setSpinnerData(1,-1,-1);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getDataName(collegeData));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        college_spin.setAdapter(arrayAdapter);

        return root;
    }

    private void setSpinnerData(int spin_switch, int college_id, int department_id) {
        DBHelper dbHelper = new DBHelper(getContext());
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (spin_switch == 1) {
            collegeData.clear();
            departmentData.clear();
            subjectData.clear();

            cursor = db.rawQuery("select * from tb_college", null);

            collegeData.add(new DBData(-1, -1, "전체", null));

            while (cursor.moveToNext()) {
                collegeData.add(new DBData(cursor.getInt(0), -1, cursor.getString(1), null));
            }
        }

        if (spin_switch == 2) {
            departmentData.clear();
            subjectData.clear();

            if (college_id == 0)
                cursor = db.rawQuery("select * from tb_department", null);
            else
                cursor = db.rawQuery("select * from tb_department where college_id = ?", new String[]{(college_id)+""});

            departmentData.add(new DBData(-1, -1, "전체", null));

            while (cursor.moveToNext()) {
                departmentData.add(new DBData(cursor.getInt(0), cursor.getInt(1) , cursor.getString(2), null));
            }
        }

        if (spin_switch == 3) {
            subjectData.clear();

            if (department_id == 0)
                cursor = db.rawQuery("select * from tb_subject", null);
            else
                cursor = db.rawQuery("select * from tb_subject where department_id = ?", new String[]{(department_id)+""});

            subjectData.add(new DBData(-1, -1, "전체", null));

            while (cursor.moveToNext()) {
                subjectData.add(new DBData(cursor.getInt(0), cursor.getInt(1),
                        cursor.getString(3), new String[]{cursor.getString(2), cursor.getString(4), cursor.getString(5)}));
            }
        }

        db.close();
    }

    private ArrayList<String> getDataName(ArrayList<DBData> dbData) {
        ArrayList<String> arrayList = new ArrayList();

        for (int i = 0; i < dbData.size(); i++) {
            if (dbData.get(i).another != null)
                arrayList.add("(" + dbData.get(i).getAnother()[0] + ")" +dbData.get(i).name);
            else arrayList.add(dbData.get(i).name);
        }

        return arrayList;
    }

    String search_collegeId;
    String search_departmentId;
    String search_subjectId;
    String search_text;
    @Override
    public boolean onQueryTextSubmit(String query) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tb_trade");
        search_collegeId = college_spin.getSelectedItemPosition() + "";
        search_departmentId = departmentData.get(department_spin.getSelectedItemPosition()).getKey() + "";
        search_subjectId = subjectData.get(subject_spin.getSelectedItemPosition()).getKey() + "";
        search_text = query;
        firebase.getList().clear();

        reference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trade trade = dataSnapshot.getValue(Trade.class);
        
                Log.d("js", "/onQueryTextSubmit: " + search_collegeId);
                Log.d("js", "onQueryTextSubmit: " + search_departmentId);
                Log.d("js", "onQueryTextSubmit:/ " + search_subjectId);

                if (trade.getBook().getCollege_id().equals(search_collegeId) || search_collegeId.equals("0") || search_collegeId.equals("-1")) {
                    if (trade.getBook().getDepartment_id().equals(search_departmentId) || search_departmentId.equals("0")|| search_departmentId.equals("-1")) {
                        if (trade.getBook().getCollege_id().equals(search_subjectId) || search_subjectId.equals("0")|| search_subjectId.equals("-1")) {
                            Log.d(TAG, "onChildAdded: ");
                            if (search_text.isEmpty() || trade.getBook().getTitle().contains(search_text)) {
                                firebase.getList().add(trade);
                            }
                        }
                    }
                }

                firebase.getRecyclerView().getAdapter().notifyDataSetChanged();

//                Log.d("JS", "onChildAdded: " + dataSnapshot.getValue(Trade.class).toString());
//                for (DataSnapshot snap: dataSnapshot.getChildren()) {
////                    Trade trade = snap.getValue(Trade.class);
//                    Log.d("JS", "onChildAdded: " + snap.getValue(Trade.class).toString());
//                }
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




        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == college_spin) {
            if (position == 0) {
                setSpinnerData(2, 0, -1);
            } else {
                setSpinnerData(2, collegeData.get(position).getKey(), -1);
//                Log.d("JS", "onItemSelected: " + collegeData.get(position).getKey());

            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getDataName(departmentData));

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            department_spin.setAdapter(arrayAdapter);
            Log.d("JS", "onItemSelected: " + parent.getItemAtPosition(position));
        } else if (parent == department_spin) {
            if (position == 0) {
                setSpinnerData(3, -1, 0);
            } else {
                setSpinnerData(3, -1, departmentData.get(position).getKey());
                Log.d("JS", "onItemSelected: " + departmentData.get(position).getKey());

            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getDataName(subjectData));

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject_spin.setAdapter(arrayAdapter);
            Log.d("JS", "onItemSelected: " + parent.getItemAtPosition(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        if (view == bookInfoBtn) {
            Intent intent = new Intent(this.getActivity(), BookInfoActivity.class);
            this.getActivity().startActivity(intent);
        }
    }

    class DBData {
        int key;
        int foreign_key;
        String name;
        String another[];

        public DBData(int key, int foreign_key, String name, String[] another) {
            this.key = key;
            this.foreign_key = foreign_key;
            this.name = name;
            this.another = another;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getForeignkey() {
            return foreign_key;
        }

        public void setForeignkey(int foreign_key) {
            this.foreign_key = foreign_key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getAnother() {
            return another;
        }

        public void setAnother(String[] another) {
            this.another = another;
        }


    }
}