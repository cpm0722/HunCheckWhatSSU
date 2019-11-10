package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class BookInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String bookInfoType = intent.getStringExtra("BookInfoType");
        Trade trade;


        if (bookInfoType == null) {
            Log.d("JS", "onCreate: 식별할 BookInfoType이 없습니다.");
            finish();
        } else if(bookInfoType.equals("BOOK_INFO_DEFAULT")) {
            setContentView(R.layout.activity_book_info);
            trade = intent.getParcelableExtra("book_info_default_data");

            Log.d("JS", "onCreate: " + trade.toString());
        } else if(bookInfoType.equals("BOOK_INFO_TRADE_DETAIL")) {
            setContentView(R.layout.activity_book_trade_detail);
            trade = intent.getParcelableExtra("book_info_trade_detail");
        } else {
            Log.d("JS", "onCreate: BookInfoActivity - unknownBookType");
            finish();
        }
    }

    protected void setData(Book book) {

    }

//    this work after Trade Class Merge
//    protected void setData(Trade trade) {
//
//    }
}
