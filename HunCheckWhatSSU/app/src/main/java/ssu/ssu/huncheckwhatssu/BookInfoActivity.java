package ssu.ssu.huncheckwhatssu;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import android.view.View;
import android.widget.Button;


import java.text.SimpleDateFormat;
import java.util.Date;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class BookInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    Trade trade;

    // Book
    TextView activity_book_info_title;
    ImageView activity_book_info_coverImg;
    TextView activity_book_info_authorText;
    TextView activity_book_info_publisherText;
    TextView activity_book_info_publicationDateText;
    TextView activity_book_info_bookCostText;
//    TextView activity_book_info_

    // Seller
    TextView activity_book_info_sellerText;
    TextView activity_book_info_sellerContactNumberText;
    TextView activity_book_info_sellerCreditRating;

    // Purchaser
    TextView activity_book_info_purchaserText;
    TextView activity_book_info_purchaserContactNumberText;
    TextView activity_book_info_purchaserCreditRating;

    // BookState
    TextView activity_book_info_state01Text;
    TextView activity_book_info_state02Text;
    TextView activity_book_info_state03Text;
    TextView activity_book_info_state04Text;
    TextView activity_book_info_state05Text;
    TextView activity_book_info_state06Text;

    // Trade Info
    TextView activity_book_info_placeAddressText;
    TextView activity_book_info_tradeDateText;
    TextView activity_book_info_tradeState;
    //MAP 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String bookInfoType = intent.getStringExtra("BookInfoType");

        if (bookInfoType == null) {
            Log.d("JS", "onCreate: 식별할 BookInfoType이 없습니다.");
            finish();
        } else if(bookInfoType.equals("BOOK_INFO_DEFAULT")) {
            setContentView(R.layout.activity_book_info);
            trade = intent.getParcelableExtra("book_info_default_data");
            initObject(1);
            setData(1);
            Log.d("JS", "onCreate: " + trade.toString());
        } else if(bookInfoType.equals("BOOK_INFO_TRADE_DETAIL")) {
            setContentView(R.layout.activity_book_trade_detail);
            trade = intent.getParcelableExtra("book_info_trade_detail");
            Log.d("JS", "onCreate: " + trade.toString());
            initObject(2);
            setData(2);
            Log.d("JS", "onCreate: " + trade.toString());
        } else {
            Log.d("JS", "onCreate: BookInfoActivity - unknownBookType");
            finish();
        }

        // naver map
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.activity_book_info_map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.activity_book_info_map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        // 숭실대학교
        LatLng latLng = new LatLng(37.49630160214827, 126.9574464751917);

        Marker marker = new Marker();
//        naverMap.setMaxZoom(13);
//        naverMap.setMinZoom(13);
        naverMap.setCameraPosition(new CameraPosition(latLng, 16));
        marker.setPosition(latLng);
        marker.setMap(naverMap);
    }

    // bookInfoType = 1 : BOOK_INFO_DEFAULT
    // bookInfoType = 2 : BOOK_INFO_TRADE_DETAIL
    protected void initObject(int bookInfoType) {
        // Book
        activity_book_info_title = findViewById(R.id.activity_book_info_title);
        activity_book_info_coverImg = findViewById(R.id.activity_book_info_coverImg);
        activity_book_info_authorText = findViewById(R.id.activity_book_info_authorText);
        activity_book_info_publisherText = findViewById(R.id.activity_book_info_publisherText);
        activity_book_info_publicationDateText = findViewById(R.id.activity_book_info_publicationDateText);
        activity_book_info_bookCostText = findViewById(R.id.activity_book_info_bookCostText);

        // Seller
        activity_book_info_sellerText = findViewById(R.id.activity_book_info_sellerText);
        activity_book_info_sellerContactNumberText = findViewById(R.id.activity_book_info_sellerContactNumberText);
        activity_book_info_sellerCreditRating = findViewById(R.id.activity_book_info_sellerCreditRating);

        // BookState
        activity_book_info_state01Text = findViewById(R.id.activity_book_info_state01Text);
        activity_book_info_state02Text = findViewById(R.id.activity_book_info_state02Text);
        activity_book_info_state03Text = findViewById(R.id.activity_book_info_state03Text);
        activity_book_info_state04Text = findViewById(R.id.activity_book_info_state04Text);
        activity_book_info_state05Text = findViewById(R.id.activity_book_info_state05Text);
        activity_book_info_state06Text = findViewById(R.id.activity_book_info_state06Text);

        // Trade Info
        activity_book_info_placeAddressText = findViewById(R.id.activity_book_info_placeAddressText);
        activity_book_info_tradeState = findViewById(R.id.activity_Trade_Info_StateText);


        if (bookInfoType == 1) {

        } else if (bookInfoType == 2) {
            // Trade Info
            activity_book_info_tradeDateText = findViewById(R.id.activity_book_info_tradeDateText);

            // Purchaser
            activity_book_info_purchaserText = findViewById(R.id.activity_book_info_purchaserText);
            activity_book_info_purchaserContactNumberText = findViewById(R.id.activity_book_info_purchaserContactNumberText);
            activity_book_info_purchaserCreditRating = findViewById(R.id.activity_book_info_purchaserCreditRating);
        }
    }

    public void setData(int bookInfoType) {
        // Book
        Log.d("JS", "setData2: " + trade.toString());

        Book book = trade.getBook();
        if (book != null) {
            Log.d("JS", "setData: A");
            activity_book_info_title.setText(book.getTitle());
            if (book.getImage() == null) {
                activity_book_info_coverImg.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
            } else Glide.with(this).load(book.getImage()).into(activity_book_info_coverImg);

            activity_book_info_authorText.setText(book.getAuthor());
            activity_book_info_publisherText.setText(book.getPublisher());

            activity_book_info_publicationDateText.setText(book.getPubDate());
            activity_book_info_bookCostText.setText(book.getOriginal_Price() + "");
        }

        // Seller

        Customer customer = trade.getSeller();
        if (customer != null) {
            Log.d("JS", "setData: B");
            activity_book_info_sellerText.setText(customer.getName());
            activity_book_info_sellerContactNumberText.setText(customer.getPhoneNumber());
            activity_book_info_sellerCreditRating.setText(customer.getCreditRating() + "");
        }

        // BookState
        BookState bookState = trade.getBook().getBookState();
        if (bookState != null) {
            Log.d("JS", "setData: C");
            activity_book_info_state01Text.setText(bookState.getBookState01().name());
            activity_book_info_state02Text.setText(bookState.getBookState02().name());
            activity_book_info_state03Text.setText(bookState.getBookState03().name());
            activity_book_info_state04Text.setText(bookState.getBookState04().name());
            activity_book_info_state05Text.setText(bookState.getBookState05().name());
            activity_book_info_state06Text.setText(bookState.getBookState06().name());
        }


        // Trade Info
        activity_book_info_placeAddressText.setText(trade.getTradePlace());

        activity_book_info_tradeState.setText(trade.getTradeStateForShowView());

        if (bookInfoType == 1) {
        } else if (bookInfoType == 2) {
            // Trade Info
            activity_book_info_tradeDateText.setText(trade.getTradeDate());

            // Purchaser
            Customer purchaser = trade.getPurchaser();
            activity_book_info_purchaserText.setText(purchaser.getName());
            activity_book_info_purchaserContactNumberText.setText(purchaser.getPhoneNumber());
            activity_book_info_purchaserCreditRating.setText(purchaser.getCreditRating() + "");

        }

        Button back2TradeOverview=findViewById(R.id.back2TradeOverview);
        back2TradeOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
