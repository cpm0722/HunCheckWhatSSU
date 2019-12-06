package ssu.ssu.huncheckwhatssu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

import ssu.ssu.huncheckwhatssu.DB.DBData;
import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;
import ssu.ssu.huncheckwhatssu.DB.DBHelper;

import static android.widget.Toast.LENGTH_SHORT;


public class AddBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button add;
    Book book1;
    Trade trade;
    TextView title;
    TextView Author;
    TextView Isbn;
    TextView PubDate;
    TextView originalPrice;
    TextView seller;
    EditText sellPrice;
    ImageView imageView;
    TextView loadAddr;

    int price1;//원가
    TextView publisher;
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6;
    BookState bookState;
    Spinner college_sp;
    Spinner department_sp;
    Spinner subject_sp;
    ArrayList<DBData> collegeData;
    ArrayList<DBData> departmentData;
    ArrayList<DBData> subjectData;
    String loadAddrStr;
    LatLng latLng;

    FirebaseCommunicator firebaseCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        college_sp = (Spinner) findViewById(R.id.college_sp);
        subject_sp = (Spinner) findViewById(R.id.subject_sp);
        department_sp = findViewById(R.id.department_sp);

        collegeData = new ArrayList<>();
        departmentData = new ArrayList<>();
        subjectData = new ArrayList<>();

        college_sp.setOnItemSelectedListener(this);
        subject_sp.setOnItemSelectedListener(this);
        department_sp.setOnItemSelectedListener(this);

        setSpinnerData(1, -1, -1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getDataName(collegeData));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        college_sp.setAdapter(arrayAdapter);

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            if (bd.getString("start") != null) {
                Toast.makeText(getApplicationContext(),
                        "" + bd.getString("start"),
                        LENGTH_SHORT).show();
            }
        }

        Button remove = findViewById(R.id.returnBack);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add = findViewById(R.id.btnDone);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sellPrice.getText().toString().length() <= 1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "판매가격을 입력하세요", LENGTH_SHORT);
                    toast.show();
                }
                else if(college_sp.getSelectedItemPosition() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "대학을 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
                else if(department_sp.getSelectedItemPosition() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "전공을 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
                else if(subject_sp.getSelectedItemPosition() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "강의를 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
                else if((radioGroup1.getCheckedRadioButtonId() == -1) || (radioGroup2.getCheckedRadioButtonId() == -1 ) || (radioGroup3.getCheckedRadioButtonId() == -1) || (radioGroup4.getCheckedRadioButtonId() == -1) || (radioGroup5.getCheckedRadioButtonId() == -1) || (radioGroup6.getCheckedRadioButtonId() == -1)){
                    Toast toast = Toast.makeText(getApplicationContext(), "책상태를 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
                else if((latLng == null)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "거래 희망 장소를 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Trade addTrade = setData();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("activity", "AddBook");
                    resultIntent.putExtra("addTrade", addTrade);
                    setResult(RESULT_OK, resultIntent);

                    //등록 버튼 누르면 종료, 데이터 추가기능 넣어야함.
                    finish();
                }
            }
        });

        firebaseCommunicator = new FirebaseCommunicator(FirebaseCommunicator.WhichRecyclerView.none);

        Intent intent = getIntent(); //그냥 trade리턴함수 만든는게..나으려나
        book1 = intent.getParcelableExtra("searched_book_data");

        title = findViewById(R.id.s_title);
        Author = findViewById(R.id.author);
        Isbn = findViewById(R.id.ISBN);
        PubDate = findViewById(R.id.p_date);
        originalPrice = findViewById(R.id.realprice);
        publisher = findViewById(R.id.s_publisher);
        seller = findViewById(R.id.seller);
        sellPrice = findViewById(R.id.sellprice);
        imageView = findViewById(R.id.activity_add_book_imageView);

        title.setText(book1.getTitle());
        Author.setText(book1.getAuthor());
        Isbn.setText(book1.getIsbn10());
        PubDate.setText(book1.getPubDate());
        originalPrice.setText(book1.getOriginalPrice() + "");
        publisher.setText(book1.getPublisher());
        Glide.with(this).load(book1.getImage()).into(imageView);

        seller.setText(firebaseCommunicator.getUser().getDisplayName());
        // ImageView image=findViewById(R.id.image);
        //  Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.id.)
        bookState = new BookState();
        radioGroup1 = findViewById(R.id.book_state_line);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST1:
                        bookState.setBookState01(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD1:
                        bookState.setBookState01(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD1:
                        bookState.setBookState01(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST1:
                        bookState.setBookState01(BookState.bookState.WORST);
                        break;
                }
            }
        });
        radioGroup2 = findViewById(R.id.book_state_write);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST2:
                        bookState.setBookState02(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD2:
                        bookState.setBookState02(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD2:
                        bookState.setBookState02(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST2:
                        bookState.setBookState02(BookState.bookState.WORST);
                        break;
                }
            }
        });
        radioGroup3 = findViewById(R.id.book_state_cover);
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST3:
                        bookState.setBookState03(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD3:
                        bookState.setBookState03(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD3:
                        bookState.setBookState03(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST3:
                        bookState.setBookState03(BookState.bookState.WORST);
                        break;
                }
            }
        });
        radioGroup4 = findViewById(R.id.book_state_name);
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST4:
                        bookState.setBookState04(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD4:
                        bookState.setBookState04(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD4:
                        bookState.setBookState04(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST4:
                        bookState.setBookState04(BookState.bookState.WORST);
                        break;
                }
            }
        });
        radioGroup5 = findViewById(R.id.book_state_color);
        radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST5:
                        bookState.setBookState05(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD5:
                        bookState.setBookState05(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD5:
                        bookState.setBookState05(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST5:
                        bookState.setBookState05(BookState.bookState.WORST);
                        break;
                }
            }
        });

        radioGroup6 = findViewById(R.id.book_state_pagegone);
        radioGroup6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST6:
                        bookState.setBookState06(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD6:
                        bookState.setBookState06(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD6:
                        bookState.setBookState06(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST6:
                        bookState.setBookState06(BookState.bookState.WORST);
                        break;
                }
            }
        });
        radioGroup1.check(R.id.book_state_BEST1);
        radioGroup2.check(R.id.book_state_BEST2);
        radioGroup3.check(R.id.book_state_BEST3);
        radioGroup4.check(R.id.book_state_BEST4);
        radioGroup5.check(R.id.book_state_BEST5);
        radioGroup6.check(R.id.book_state_BEST6);

        loadAddr = findViewById(R.id.loadAddr);
        loadAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), SearchPlaceActivity.class), SearchPlaceActivity.SEARCH_PLACE_ACITIVITY_REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SearchPlaceActivity.SEARCH_PLACE_ACITIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    loadAddrStr = data.getStringExtra("SelectedAddress");
                    latLng = data.getParcelableExtra("Location");

                    this.loadAddr.setText(loadAddrStr);
                }
                break;
        }
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

   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == college_sp) {
            if (position == 0) {
                setSpinnerData(2, 0, -1);
            } else {
                setSpinnerData(2, collegeData.get(position).getKey(), -1);

            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getDataName(departmentData));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            department_sp.setAdapter(arrayAdapter);
        } else if (parent == department_sp) {
            if (position == 0) {
                setSpinnerData(3, -1, 0);
            } else {
                setSpinnerData(3, -1, departmentData.get(position).getKey());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getDataName(subjectData));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject_sp.setAdapter(arrayAdapter);
           }
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setSpinnerData(int spin_switch, int college_id, int department_id) {
        DBHelper dbHelper = new DBHelper(this);
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





    public Trade setData() { //Trade형 객체 데이터베이스 올리기 위해 객체화작업.
        int college = collegeData.get(college_sp.getSelectedItemPosition()).getKey();
        int department = departmentData.get(department_sp.getSelectedItemPosition()).getKey();
        int subject = subjectData.get(subject_sp.getSelectedItemPosition()).getKey();
        if(college > 0) book1.setCollege_id(college + "");
        else book1.setCollege_id(null);
        if(department > 0) book1.setDepartment_id(department + "");
        else book1.setDepartment_id(null);
        if(subject > 0) book1.setSubject_id(subject + "");
        else book1.setSubject_id(null);
        book1.setBookState(bookState);
        String sellingPrice = sellPrice.getText().toString();
        if(sellingPrice.length() > 1) {
            trade = new Trade(book1, firebaseCommunicator.getUserPath(),new Integer(sellingPrice).intValue());
        }
        else{
            trade = new Trade(book1, firebaseCommunicator.getUserPath());
        }
        trade.setSellerRate(-1);
        trade.setPurchaserRate(-1);

        trade.setTradePlace(loadAddr.getText().toString());
        trade.setLatitude(latLng.latitude);
        trade.setLongitude(latLng.longitude);
        return trade;

    }



}

