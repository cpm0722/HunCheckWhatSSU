package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.NaverBookSearch;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class AddBookActivity extends AppCompatActivity {
    Button add;
    Book book1;
    Spinner s1;
    Spinner s2;
    Trade trade;
    TextView title;
    TextView Author;
    TextView Isbn;
    TextView PubDate;
    TextView price;
    int price1;//원가
    TextView publisher;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioGroup radioGroup3;
    RadioGroup radioGroup4;
    RadioGroup radioGroup5;
    RadioGroup radioGroup6;
    BookState bookState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.college, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (s1.getSelectedItem().equals("IT대")) {
                    Toast.makeText(getApplicationContext(), "IT대", Toast.LENGTH_SHORT).show();
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(AddBookActivity.this, R.array.ITcollege, android.R.layout.simple_spinner_item);
                    s2.setAdapter(adapter2);
                } else if (s1.getSelectedItem().equals("공과대")) {
                    Toast.makeText(getApplicationContext(), "공과대", Toast.LENGTH_SHORT).show();
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(AddBookActivity.this, R.array.EngineeringCollege, android.R.layout.simple_spinner_item);
                    s2.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

