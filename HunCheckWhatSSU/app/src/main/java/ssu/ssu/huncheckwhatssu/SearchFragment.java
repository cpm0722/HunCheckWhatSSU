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
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.ArrayList;

import ssu.ssu.huncheckwhatssu.DB.DBData;
import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {
    SearchFirebaseCommunicator firebase;
    Spinner college_spin;
    Spinner department_spin;
    Spinner subject_spin;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<DBData> collegeData;
    ArrayList<DBData> departmentData;
    ArrayList<DBData> subjectData;

    private RecyclerView recyclerView;

    static final int MAP_REQUEST_CODE = 9910;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("HunCheckWhatSSU-책 검색");

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        swipeRefreshLayout = root.findViewById(R.id.swipe_fragment_search);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //새로 고침할 작업 나중에 추가하기
                swipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, "recyclerview: swipe&Refresh");

                // search refresh
                onQueryTextSubmit(search_text);
            }
        });

        //SearchFirebaseCommunicator 생성
        firebase = new SearchFirebaseCommunicator("trade", this.getContext(), this.getActivity(), recyclerView);

        // Recycler View Click Listener
        RecyclerTouchListener onTouchListener = new RecyclerTouchListener(this.getActivity(), recyclerView);
        onTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                Trade trade = firebase.getList().get(position);

                Intent intent = new Intent(getContext(), BookInfoActivity.class);
                intent.putExtra("BookInfoType", "BOOK_INFO_DEFAULT");
                intent.putExtra("book_info_default_data", trade);

                getContext().startActivity(intent);

            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {
            }
        });


        recyclerView.addOnItemTouchListener(onTouchListener);


//        TEST용 Firebase 추가 버튼

//        Button addBtn = root.findViewById(R.id.fragment_add_btn);
//        addBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(getContext(), SearchPlaceActivity.class),MAP_REQUEST_CODE);
//            }
//        });

        setFirebaseEvent();


        // 상단 카테고리 작업
        collegeData = new ArrayList<>();
        departmentData = new ArrayList<>();
        subjectData = new ArrayList<>();

        setSpinnerData(1, -1, -1);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getDataName(collegeData));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        college_spin.setAdapter(arrayAdapter);

        return root;
    }

    private void setFirebaseEvent() {
        // Firebase
        final DatabaseReference myRef = firebase.getMyRef();
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("customer");
        final String eventKey = "combine_data";
        myRef.limitToLast(100);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    for (DataSnapshot tradeSnap : dataSnapshot.getChildren()) {
                        Trade trade = tradeSnap.getValue(Trade.class);

                        if (trade.getTradeState() != Trade.TradeState.WAIT)
                            continue;

                        firebase.getList().add(trade);
                        trade.setSeller(new Customer(trade.getSellerId()));


                        trade.getSeller().setCustomerDataFromUID(firebase.getRecyclerView().getAdapter());

                        if (firebase.getRecyclerView() != null)
                            firebase.getRecyclerView().getAdapter().notifyDataSetChanged();

                        Log.d("JS", "onDataChange: " + trade.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*SearchFragment에서 학부>학과인 거 번호를 읽어와서 해야함.*/
    /*승주_Uri Trade에서 넣으면, Uri naverbooksearch_activity_image Uri 를 참고해서 쓰면 됨 */
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
                cursor = db.rawQuery("select * from tb_department where college_id = ?", new String[]{(college_id) + ""});

            departmentData.add(new DBData(-1, -1, "전체", null));

            while (cursor.moveToNext()) {
                departmentData.add(new DBData(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), null));
            }
        }

        if (spin_switch == 3) {
            subjectData.clear();

            if (department_id == 0)
                cursor = db.rawQuery("select * from tb_subject", null);
            else
                cursor = db.rawQuery("select * from tb_subject where department_id = ?", new String[]{(department_id) + ""});

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
            if (dbData.get(i).getAnother() != null)
                arrayList.add("(" + dbData.get(i).getAnother()[0] + ")" + dbData.get(i).getName());
            else arrayList.add(dbData.get(i).getName());
        }

        return arrayList;
    }

    String search_collegeId;
    String search_departmentId;
    String search_subjectId;
    String search_text;

    @Override
    public boolean onQueryTextSubmit(String query) {

        search_collegeId = college_spin.getSelectedItemPosition() + "";
        search_departmentId = departmentData.get(department_spin.getSelectedItemPosition()).getKey() + "";
        search_subjectId = subjectData.get(subject_spin.getSelectedItemPosition()).getKey() + "";
        search_text = (query == null ? "" : query);
        firebase.getList().clear();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("trade");

        reference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trade trade = dataSnapshot.getValue(Trade.class);

                trade.setSeller(new Customer(trade.getSellerId()));

                Log.d(TAG, "onChildAdded: ");

                Log.d("js", "/onQueryTextSubmit: " + search_collegeId);
                Log.d("js", "onQueryTextSubmit: " + search_departmentId);
                Log.d("js", "onQueryTextSubmit:/ " + search_subjectId);

                // 거래 대기중인 것만 나타남
                if (trade.getTradeState() != Trade.TradeState.WAIT) return;

                if (trade.getBook().getCollege_id().equals(search_collegeId) || search_collegeId.equals("0") || search_collegeId.equals("-1")) {
                    if (trade.getBook().getDepartment_id().equals(search_departmentId) || search_departmentId.equals("0") || search_departmentId.equals("-1")) {
                        if (trade.getBook().getSubject_id().equals(search_subjectId) || search_subjectId.equals("0") || search_subjectId.equals("-1")) {
                            Log.d(TAG, "onChildAdded: ");
                            if (search_text.isEmpty() || trade.getBook().getTitle().contains(search_text)) {
                                Log.d(TAG, "onChildAdded: " + firebase);
                                trade.getSeller().setCustomerDataFromUID(firebase.getRecyclerView().getAdapter());
                                firebase.getList().add(trade);
                            }
                        }
                    }
                }

                firebase.getRecyclerView().getAdapter().notifyDataSetChanged();
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
        search_text = newText;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Log.d(TAG, "onActivityResult: dwdwd " + requestCode + ", " + resultCode);
//
//        if (requestCode == MAP_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Log.d(TAG, "onActivityResult: " + data.getStringExtra("SelectedAddress"));
//                Log.d(TAG, "onActivityResult: " + ((LatLng) data.getParcelableExtra("Location")).toString());
//            }
//        }
    }
}



