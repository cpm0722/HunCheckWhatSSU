package ssu.ssu.huncheckwhatssu;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class BookInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    Context context;

    Trade trade;

    // Book
    TextView activity_book_info_title;
    ImageView activity_book_info_coverImg;
    TextView activity_book_info_authorText;
    TextView activity_book_info_publisherText;
    TextView activity_book_info_publicationDateText;
    TextView activity_book_info_bookCostText;

    // Seller
    TextView activity_book_info_sellerText;
    TextView activity_book_info_sellerContactNumberText;
    TextView activity_book_info_sellerCreditRating;
    TextView activity_book_info_sellerCount;
    ImageView activity_book_info_sellerCreditRatingImage;
    LinearLayout activity_book_info_sellerGroupLabel;
    LinearLayout activity_book_info_sellerContent;

    // Purchaser
    TextView activity_book_info_purchaserText;
    TextView activity_book_info_purchaserContactNumberText;
    TextView activity_book_info_purchaserCreditRating;
    TextView activity_book_info_purchaserCount;
    ImageView activity_book_info_purchaserCreditRatingImage;
    LinearLayout activity_book_info_purchaserGroupLabel;
    LinearLayout activity_book_info_purchaserContent;

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

    LinearLayout sellerLayout;
    LinearLayout purchaserLayout;

    Button sendPurchaseRequestBtn;
    Button tradeCompleteBtn;
    Button back2TradeOverview;

    Intent resultIntent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        Intent intent = getIntent();
        String bookInfoType = intent.getStringExtra("BookInfoType");
        position = intent.getIntExtra("position", -1);
        resultIntent = new Intent(this, MainActivity.class);

        if (bookInfoType == null) {
            Log.d("JS", "onCreate: 식별할 BookInfoType이 없습니다.");
            finish();
        } else if (bookInfoType.equals("BOOK_INFO_DEFAULT")) {
            setContentView(R.layout.activity_book_info);
            trade = intent.getParcelableExtra("book_info_default_data");
            initObject(1);
            setData(1);
            Log.d("JS", "onCreate: " + trade.toString());
        } else if (bookInfoType.equals("BOOK_INFO_TRADE_DETAIL")) {
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
        activity_book_info_sellerCount = findViewById(R.id.activity_book_info_sellerCount);
        activity_book_info_sellerCreditRatingImage = findViewById(R.id.activity_book_info_sellerCreditRatingImage);
        activity_book_info_sellerGroupLabel = findViewById(R.id.activity_book_info_sellerGroupLabel);
        activity_book_info_sellerContent = findViewById(R.id.activity_book_info_sellerContent);

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

        View.OnClickListener customerLayoutTouchListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view == sellerLayout) {
                    String id = trade.getSellerId();
                } else if (view == purchaserLayout) {
                    String id = trade.getPurchaserId();
                }
            }
        };

        sellerLayout = findViewById(R.id.activity_book_info_sellerGroupLabel);


        if (bookInfoType == 1) {

        } else if (bookInfoType == 2) {
            // Trade Info
            activity_book_info_tradeDateText = findViewById(R.id.activity_book_info_tradeDateText);

            // Purchaser
            activity_book_info_purchaserText = findViewById(R.id.activity_book_info_purchaserText);
            activity_book_info_purchaserContactNumberText = findViewById(R.id.activity_book_info_purchaserContactNumberText);
            activity_book_info_purchaserCreditRating = findViewById(R.id.activity_book_info_purchaserCreditRating);
            activity_book_info_purchaserCount = findViewById(R.id.activity_book_info_purchaserCount);
            activity_book_info_purchaserCreditRatingImage = findViewById(R.id.activity_book_info_purchaserCreditRatingImage);
            activity_book_info_purchaserGroupLabel = findViewById(R.id.activity_book_info_purchaserGroupLabel);
            activity_book_info_purchaserContent = findViewById(R.id.activity_book_info_purchaserContent);

            purchaserLayout = findViewById(R.id.activity_book_info_purchaserGroupLabel);
        }
    }

    public void setCreditRatingImage(ImageView imageView, double creditRating) {
        if (creditRating < 2.5)
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_bad));
        else if (creditRating <= 3.5)
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_soso));
        else
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_good));
        return;
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
            activity_book_info_bookCostText.setText(book.getOriginalPrice() + "");
        }

        //Click Listener
        View.OnClickListener layoutClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(view == activity_book_info_sellerContent || view == activity_book_info_sellerGroupLabel){
                    Intent intent = new Intent(context, EvaluationActivity.class);
                    intent.putExtra("id", trade.getSellerId());
                    startActivity(intent);
                }
                else if(view == activity_book_info_purchaserContent || view == activity_book_info_purchaserGroupLabel){
                    Intent intent = new Intent(context, EvaluationActivity.class);
                    intent.putExtra("id", trade.getPurchaserId());
                    startActivity(intent);
                }
            }
        };

        // Seller

        Customer customer = trade.getSeller();
        if (customer != null) {
            Log.d("JS", "setData: B");
            activity_book_info_sellerText.setText(customer.getName());
            activity_book_info_sellerContactNumberText.setText(customer.getPhoneNumber());
            activity_book_info_sellerCreditRating.setText(String.format("%.2f", customer.getCreditRating()) + "");
            activity_book_info_sellerCount.setText(customer.getCancelCount() + "/" + customer.getTradeCount() + "건");
            setCreditRatingImage(activity_book_info_sellerCreditRatingImage, customer.getCreditRating());
            activity_book_info_sellerGroupLabel.setOnClickListener(layoutClickListener);
            activity_book_info_sellerContent.setOnClickListener(layoutClickListener);
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
            sendPurchaseRequestBtn = findViewById(R.id.send_purchase_request_btn);
            sendPurchaseRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseHelper firebaseHelper = new FirebaseHelper();
                    FirebaseUser me = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = me.getDisplayName() + "_" + me.getUid();
                    if (trade.getTradeId() != null) {
                        firebaseHelper.sendPurchaseRequest(trade.getTradeId(), uid);
                        Log.d("HUMCHECKYC", "tradeId null 아님 ");
                    }
                    Toast.makeText(getApplicationContext(), "구매요청이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else if (bookInfoType == 2) {
            // Trade Info
            activity_book_info_tradeDateText.setText(trade.getTradeDate());
            // Purchaser
            Customer purchaser = trade.getPurchaser();
            if (purchaser == null) {
                purchaser = new Customer(trade.getPurchaserId());
                purchaser.setCustomerDataFromUID(null);
            }
            activity_book_info_purchaserText.setText(purchaser.getName());
            activity_book_info_purchaserContactNumberText.setText(purchaser.getPhoneNumber());
            activity_book_info_purchaserCreditRating.setText(String.format("%.2f", purchaser.getCreditRating()) + "");
            activity_book_info_purchaserCount.setText(purchaser.getCancelCount() + "/" + purchaser.getTradeCount() + "건");
            setCreditRatingImage(activity_book_info_purchaserCreditRatingImage, purchaser.getCreditRating());
            activity_book_info_purchaserGroupLabel.setOnClickListener(layoutClickListener);
            activity_book_info_purchaserContent.setOnClickListener(layoutClickListener);

        }

        back2TradeOverview = findViewById(R.id.back2TradeOverview);
        back2TradeOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (bookInfoType == 2) {
            tradeCompleteBtn = findViewById(R.id.tradeComplete);
            if (trade.getTradeState() == Trade.TradeState.COMPLETE) {
                tradeCompleteBtn.setVisibility(View.GONE);
            } else {
                tradeCompleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        trade.setTradeState(Trade.TradeState.COMPLETE);
                        FirebaseCommunicator.editTrade(trade);
                        Toast.makeText(getApplicationContext(), "거래 완료", Toast.LENGTH_SHORT).show();
                        resultIntent.putExtra("position", position);
                        resultIntent.putExtra("trade", trade);
                        setResult(RESULT_OK, resultIntent);
                        finish();

                    }

                });
            }
        }
    }
}
