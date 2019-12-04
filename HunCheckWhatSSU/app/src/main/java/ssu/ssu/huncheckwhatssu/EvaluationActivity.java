package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EvaluationActivity extends AppCompatActivity {
    private Intent intent;
    private String id;
    private static String TAG = "DEBUG!";

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Button backButton;
    private EvaluationAdapter adapter;

    private FirebaseCommunicator firebaseCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        intent = getIntent();
        id = intent.getStringExtra("id");
        recyclerView = findViewById(R.id.recycler_view_evaluation);
        Log.d(TAG, "onCreate: " + id);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        firebaseCommunicator = new FirebaseCommunicator(id, this, this, recyclerView);
        recyclerView.setAdapter(new EvaluationAdapter(this, firebaseCommunicator.getDoneTradeListVector(), id));
        backButton = findViewById(R.id.activity_evaluation_back_button);
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
