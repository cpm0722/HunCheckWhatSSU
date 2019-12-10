package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;

public class Lab06_2Activity extends AppCompatActivity implements View.OnClickListener {
    EditText titleView;
    EditText contentView;
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab06_2);
        titleView = findViewById(R.id.realm_add_title);
        contentView = findViewById(R.id.realm_add_content);
        addBtn = findViewById(R.id.realm_add_btn);
        addBtn.setOnClickListener(this);
    }
    public void onClick(View v) {
        final String title = titleView.getText().toString();
        final String content = contentView.getText().toString();
        Realm.init (this);
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MemoVO vo = realm.createObject(MemoVO.class);
                vo.title = title;
                vo.content = content;
            }
        });
        Intent intent = new Intent(this, RealmReadActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}