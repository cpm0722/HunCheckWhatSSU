package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static java.security.AccessController.getContext;

public class EvaluationActivity extends AppCompatActivity {
    private Intent intent;
    private String id;
    private boolean isMyId;
    private static String TAG = "DEBUG!";

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private EvaluationAdapter adapter;
    private Button backButton;

    private FirebaseCommunicator firebaseCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        intent = getIntent();
        id = intent.getStringExtra("id");
        if (id.equals(FirebaseCommunicator.getMyId()))
            isMyId = true;
        else
            isMyId = false;
        recyclerView = findViewById(R.id.recycler_view_evaluation);
        Log.d(TAG, "onCreate: " + id);
        Log.d(TAG, "onCreate: " + isMyId);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        firebaseCommunicator = new FirebaseCommunicator(id, this, this, recyclerView);
        adapter = new EvaluationAdapter(this, firebaseCommunicator.getDoneTradeListVector(), id, isMyId);
        adapter.setSwipeable(this, this, recyclerView);
        recyclerView.setAdapter(adapter);
        backButton = findViewById(R.id.activity_evaluation_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
