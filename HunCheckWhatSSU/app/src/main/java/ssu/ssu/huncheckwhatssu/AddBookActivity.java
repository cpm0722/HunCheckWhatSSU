package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddBookActivity extends AppCompatActivity {
   Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Bundle bd=getIntent().getExtras();
        if(bd !=null)
        { if(bd.getString("start")!=null){
            Toast.makeText(getApplicationContext(),
                    ""+bd.getString("start"),
                    Toast.LENGTH_SHORT).show();
        }
        }
        Button add=findViewById(R.id.btnDone);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//등록 버튼 누르면 종료, 데이터 추가기능 넣어야함.
                finish();
            }
        });
    }


}
