package ssu.ssu.huncheckwhatssu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static android.widget.Toast.LENGTH_SHORT;

public class SelectDate extends AppCompatActivity{
    Button back;
    Button selectdate;
    EditText editText;
    Trade trade;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        back=(Button)findViewById(R.id.notselectdate);
        selectdate=(Button)findViewById(R.id.selectdate);
        editText=(EditText)findViewById(R.id.editText);
        calendarView=(CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = year + "/" + (month + 1) + "/" + dayOfMonth;
                editText.setText(date); // 선택한 날짜로 설정
            }
        });


        Intent intent=getIntent();
        trade=intent.getParcelableExtra("ToputDate");
       back.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
                finish();
           }
       });
        selectdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                trade.setTradeDate("190211");
                Intent intent=new Intent();
                intent.putExtra("Trade",trade);
                /*firebase에 올려야함*/
                finish();
            }
        });
    }


}
