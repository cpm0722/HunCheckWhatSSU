package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class EditSell extends AppCompatActivity {
    TextView title;
    TextView author;
    TextView publisher;
    TextView publish_date;
    TextView isbn;
    TextView realPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sell);

        title = findViewById(R.id.s_title);
        author = findViewById(R.id.author);
        publisher = findViewById(R.id.s_publisher);
        publish_date = findViewById(R.id.p_date);
        isbn = findViewById(R.id.ISBN);
        realPrice = findViewById(R.id.originalPrice);

        Intent intent = getIntent();
        Trade trade = intent.getParcelableExtra("book_info_sell_edit_detail");

        title.setText(trade.getBook().getTitle());
        author.setText(trade.getBook().getAuthor());
        publisher.setText(trade.getBook().getPublisher());
        publish_date.setText(trade.getBook().getPubDate());
        isbn.setText(trade.getBook().getIsbn10());
        realPrice.setText(String.valueOf(trade.getBook().getOriginalPrice()));

        Button btn_back2Sell = findViewById(R.id.back2Sell);
        btn_back2Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*생명주기에 맞는거같음*/
                finish();
            }
        });
        Button btn_modifyback2Sell=findViewById(R.id.modifyback2Sell);
        btn_modifyback2Sell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               /*수정된 데이터가 DB로 보내지고 수정된 데이터를 보여줘야함*/
                finish();
            }
        });


    }


}
