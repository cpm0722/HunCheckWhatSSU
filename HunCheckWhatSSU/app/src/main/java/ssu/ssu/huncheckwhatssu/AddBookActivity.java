package ssu.ssu.huncheckwhatssu;

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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;
import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.SearchFragment.DBData;

public class AddBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button add;
    Book book1;
    Trade trade;
    TextView title;
    TextView Author;
    TextView Isbn;
    TextView PubDate;
    TextView price;
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
                        Toast.LENGTH_SHORT).show();
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
                Trade addTrade = setData();
                //sellRecyclerView = root.findViewById(R.id.sell_list) ;

                //등록 버튼 누르면 종료, 데이터 추가기능 넣어야함.
                finish();
            }
        });
        Intent intent = getIntent(); //그냥 trade리턴함수 만든는게..나으려나
        title = findViewById(R.id.s_title);
        title.setText(intent.getStringExtra("booktitle"));
        Author = findViewById(R.id.author);
        Author.setText(intent.getStringExtra("Author"));
        Isbn = findViewById(R.id.ISBN);
        Isbn.setText(intent.getStringExtra("ISBN"));
        PubDate = findViewById(R.id.p_date);
        PubDate.setText(intent.getStringExtra("p_date"));
        price = findViewById(R.id.realprice);
        price1 = intent.getIntExtra("price", 0);
        price.setText("" + price1);
        publisher = findViewById(R.id.s_publisher);
        publisher.setText(intent.getStringExtra("publisher"));
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


    }


    private ArrayList<String> getDataName(ArrayList<SearchFragment.DBData> dbData) {
        ArrayList<String> arrayList = new ArrayList();

        for (int i = 0; i < dbData.size(); i++) {
            if (dbData.get(i).another != null)
                arrayList.add("(" + dbData.get(i).getAnother()[0] + ")" + dbData.get(i).name);
            else arrayList.add(dbData.get(i).name);
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
        Book book1 = new Book();
        Customer customer1 = new Customer();
        book1.setTitle((String) title.getText());
        book1.setAuthor((String) Author.getText());
        book1.setIsbn10((String) Isbn.getText());
        book1.setOriginal_Price(price1);//원가
        book1.setPublisher((String) publisher.getText());
        book1.setPubDate((String) PubDate.getText());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userPath = user.getDisplayName() + "_" + user.getUid();
        trade = new Trade(book1, userPath);
        return trade;

    }



}

