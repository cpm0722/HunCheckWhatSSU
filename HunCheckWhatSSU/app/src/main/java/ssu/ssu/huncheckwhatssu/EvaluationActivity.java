package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EvaluationActivity extends AppCompatActivity {
    Intent intent;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        intent = getIntent();
        id = intent.getStringExtra("id");
        Log.d("DEBUG!", id);
    }
}
