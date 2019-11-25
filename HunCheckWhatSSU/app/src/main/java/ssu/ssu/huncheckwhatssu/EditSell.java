package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditSell extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sell);


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
