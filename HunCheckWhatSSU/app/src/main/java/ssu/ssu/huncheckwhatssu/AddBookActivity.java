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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        s1=(Spinner)findViewById(R.id.spinner1);
        s2=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.college,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             if(s1.getSelectedItem().equals("IT대")){
                 Toast.makeText(getApplicationContext(),"IT대",Toast.LENGTH_SHORT).show();
                 ArrayAdapter adapter2=ArrayAdapter.createFromResource(AddBookActivity.this,R.array.ITcollege,android.R.layout.simple_spinner_item);
                 s2.setAdapter(adapter2);
             }
             else if(s1.getSelectedItem().equals("공과대")){
                    Toast.makeText(getApplicationContext(),"공과대",Toast.LENGTH_SHORT).show();
                    ArrayAdapter adapter2=ArrayAdapter.createFromResource(AddBookActivity.this,R.array.EngineeringCollege,android.R.layout.simple_spinner_item);
                    s2.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    /*   ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.college,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        s1.setOnItemClickListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String > s= Arrays.asList(getResources().getStringArray(R.array.department));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //s1.setOnItemClickListener(this);
       /* ArrayAdapter adapter2= ArrayAdapter.createFromResource(this,R.array.department,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);*/

       /*
         Spinner s=(Spinner)findViewById(R.id.spinner2);
        s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(""+ parent.getItemAtPosition(position));
                여기서 customer 객체에 학부추가하도록 작업...
            }

        });
*/
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
                Trade addTrade=setData();
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
        publisher=findViewById(R.id.s_publisher);
        publisher.setText(intent.getStringExtra("publisher"));
       // ImageView image=findViewById(R.id.image);
      //  Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.id.)
    }

    public Trade setData(){ //Trade형 객체 데이터베이스 올리기 위해 객체화작업.
        Book book1=new Book();
        Customer customer1=new Customer();
        book1.setTitle((String)title.getText());
        book1.setAuthor((String)Author.getText());
        book1.setIsbn10((String)Isbn.getText());
        book1.setPrice(price1);//원가
        book1.setPublisher((String)publisher.getText());
        book1.setPubDate((String)PubDate.getText());
        customer1.setName("승주");
        trade= new Trade(book1,customer1);
        return trade;

    }
    }

