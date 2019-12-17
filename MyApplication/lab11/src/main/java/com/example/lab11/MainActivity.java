package com.example.lab11;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnMyChangeListener{

    View barView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPlusMinusView plusMinusView = findViewById(R.id.customView);
        barView = findViewById(R.id.barView);
        // 인터페이스를 구현한 객체를 View에 등록
        plusMinusView.setOnMyChangeListener(this);
    }

    @Override
    public void onChange(int value) {
        if (value < 0) {
            barView.setBackgroundColor(Color.RED);
        } else if (value < 30) {
            barView.setBackgroundColor(Color.YELLOW);
        } else if (value < 60) {
            barView.setBackgroundColor(Color.BLACK);
        } else {
            barView.setBackgroundColor(Color.GREEN);
        }
    }
}
