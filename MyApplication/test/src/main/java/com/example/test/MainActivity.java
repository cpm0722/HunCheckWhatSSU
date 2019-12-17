package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    int num;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        num = 1;
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view ==button) {
            Intent intent = new Intent(this, Servicetest.class);
            intent.putExtra("num", num);
            num++;
            startService(intent);
        }
    }
}
