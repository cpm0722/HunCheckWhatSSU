package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static ssu.ssu.huncheckwhatssu.utilClass.BookState.bookState.BEST;

public class EditSell extends AppCompatActivity {
    Trade trade;
    Customer customer;
    Book book;

    Button btn_modifyback2Sell;
    Button btn_back2Sell;
//Book
    TextView activity_book_edit_title;
    ImageView activity_book_edit_coverImg;
    TextView activity_book_edit_authorText;
    TextView activity_book_edit_publisherText;
    TextView activity_book_edit_publicationDateText;
    TextView activity_book_edit_bookCostText;
    TextView activity_book_edit_bookSellingpriceText;

    // BookState
    RadioGroup activity_book_edit_state01;
    RadioGroup activity_book_edit_state02;
    RadioGroup activity_book_edit_state03;
    RadioGroup activity_book_edit_state04;
    RadioGroup activity_book_edit_state05;
    RadioGroup activity_book_edit_state06;


    Intent resultIntent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sell);


        /*recyclerview의 각 아이템 받기*/
        Intent intent = getIntent();
        trade = intent.getParcelableExtra("book_info_sell_edit_detail");
        book = trade.getBook();
        position = intent.getIntExtra("position", -1);

        //return할 Intent 생성
        resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("activity", "EditSell");
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);

        Button btn_back2Sell = findViewById(R.id.back2Sell);
        findviews();
        makeclickable();
        setdata(trade);

    }

    public void setdata(Trade trade) {
       /*기존 데이터 그대로 보여주기*/
        activity_book_edit_coverImg .getDrawable();
        activity_book_edit_title.setText(trade.getBook().getTitle());
        activity_book_edit_authorText .setText(trade.getBook().getAuthor());
        activity_book_edit_publisherText .setText(trade.getBook().getPublisher());
        activity_book_edit_publicationDateText .setText(trade.getBook().getPubDate());
        activity_book_edit_bookCostText .setText(""+trade.getBook().getOriginalPrice());
        /*새로운 데이터 보여주기*/
        activity_book_edit_bookSellingpriceText.setText(""+trade.getSellingPrice());
        // BookState
        // activity_book_edit_state01.getCheckedRadioButtonId();라디오 그룹에서 선택된 라디오 버튼의 아이디를 반환
        //id.setChecked(true);:아이디.setChecked
    }

    private void findviews() {
        activity_book_edit_coverImg = (ImageView) findViewById(R.id.activity_book_edit_coverImg);
        activity_book_edit_title = (TextView) findViewById(R.id.activity_book_edit_title);
        activity_book_edit_authorText = (TextView)findViewById(R.id.activity_book_edit_authorText);
        activity_book_edit_publisherText = (TextView)findViewById(R.id.activity_book_edit_publisherText);
        activity_book_edit_publicationDateText = (TextView)findViewById(R.id.activity_book_edit_publicationDateText);
        activity_book_edit_bookCostText =(TextView)findViewById(R.id.activity_book_edit_bookCostText);

        activity_book_edit_bookSellingpriceText=(TextView)findViewById(R.id.activity_book_edit_bookSellingpriceText);
        // BookState
        activity_book_edit_state01 = (RadioGroup) findViewById(R.id.book_state_line);
        activity_book_edit_state02 = (RadioGroup)findViewById(R.id.book_state_write);
        activity_book_edit_state03 = (RadioGroup)findViewById(R.id.book_state_cover);
        activity_book_edit_state04 = (RadioGroup)findViewById(R.id.book_state_name);
        activity_book_edit_state05 = (RadioGroup)findViewById(R.id.book_state_color);
        activity_book_edit_state06 = (RadioGroup)findViewById(R.id.book_state_pagegone);

        btn_back2Sell = (Button)findViewById(R.id.back2Sell);
        btn_modifyback2Sell=(Button)findViewById(R.id.modifyback2Sell);


    }

    private void makeclickable(){
        /*수정 삭제 버튼 달기*/
        btn_back2Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*생명주기에 맞는거같음*/
                Toast toast=Toast.makeText(getApplicationContext(),"수정취소",Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
        btn_modifyback2Sell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                trade.setSellingPrice(Integer.parseInt((activity_book_edit_bookSellingpriceText.getText()).toString()));
                resultIntent.putExtra("trade", trade);
                /*수정된 데이터가 DB로 보내지고 수정된 데이터를 보여줘야함*/
                finish();
            }
        });

        /*라디오그룹에 seOnCheckedChangeListener달기*/
        activity_book_edit_state01.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST1:
                        book.getBookState().setBookState01(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD1:
                        book.getBookState().setBookState01(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD1:
                        book.getBookState().setBookState01(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST1:
                        book.getBookState().setBookState01(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state02.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST2:
                        book.getBookState().setBookState02(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD2:
                        book.getBookState().setBookState02(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD2:
                        book.getBookState().setBookState02(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST2:
                        book.getBookState().setBookState02(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state03.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST3:
                        book.getBookState().setBookState03(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD3:
                        book.getBookState().setBookState03(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD3:
                        book.getBookState().setBookState03(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST3:
                        book.getBookState().setBookState03(BookState.bookState.WORST);
                        break;
                }
            }
        });

        activity_book_edit_state04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST4:
                        book.getBookState().setBookState03(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD4:
                        book.getBookState().setBookState03(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD4:
                        book.getBookState().setBookState03(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST4:
                        book.getBookState().setBookState03(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state05.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST5:
                        book.getBookState().setBookState03(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD5:
                        book.getBookState().setBookState03(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD5:
                        book.getBookState().setBookState03(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST5:
                        book.getBookState().setBookState03(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state06.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST6:
                        book.getBookState().setBookState03(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD6:
                        book.getBookState().setBookState03(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD6:
                        book.getBookState().setBookState03(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST6:
                        book.getBookState().setBookState03(BookState.bookState.WORST);
                        break;
                }
            }
        });
    }
}
