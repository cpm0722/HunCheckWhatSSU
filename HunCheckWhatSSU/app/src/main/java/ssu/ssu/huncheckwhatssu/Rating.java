package ssu.ssu.huncheckwhatssu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class Rating extends AppCompatActivity {
    private Context context;

    private Trade trade;
    private String tradeId;

    private String myId;
    private float nowRating;

    private boolean isSeller;

    private String counterpartId;
    private Customer counterpart;
    private double counterpartRating;
    private int counterpartEvaluationCount;

    private EditText inputComment;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        final TextView textView=(TextView)findViewById(R.id.rateMessage);
        RatingBar rb=(RatingBar)findViewById(R.id.ratingBar);
        Button bt=(Button)findViewById(R.id.fin);
        inputComment = findViewById(R.id.comment_edit_text);
        context = this;
        counterpartRating = -1;
        counterpartEvaluationCount = -1;
        nowRating = 3;
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                nowRating = rating;
                textView.setText("평가점수:"+rating);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counterpartRating == -1){
                    Toast toast = Toast.makeText(context, "아직 상대방 정보를 불러오지 못했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("확인 ");
                    alert.setMessage("정말로 현재 점수로 평가하시겠습니까?\n 평가는 되돌릴 수 없습니다.");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            double result = (counterpartRating * counterpartEvaluationCount + nowRating) / (counterpartEvaluationCount + 1);
                            comment = inputComment.getText().toString();
                            FirebaseDatabase.getInstance().getReference().child("customer").child(counterpartId).child("CreditRating").setValue(result);
                            FirebaseDatabase.getInstance().getReference().child("customer").child(counterpartId).child("evaluationCount").setValue(counterpartEvaluationCount + 1);
                            if(isSeller) {
                                FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("purchaserRate").setValue(nowRating);
                                FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("purchaserComment").setValue(comment);
                            }
                            else{
                                FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("sellerRate").setValue(nowRating);
                                FirebaseDatabase.getInstance().getReference().child("trade").child(tradeId).child("sellerComment").setValue(comment);
                            }
                            finish();
                        }
                    });
                    alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    alert.show();
                }
            }
        });

        Intent intent = getIntent();
        trade = intent.getParcelableExtra("trade");
        tradeId = trade.getTradeId();
        myId = FirebaseCommunicator.getMyId();
        if(trade.getPurchaserId().equals(myId)){
            isSeller = false;
            counterpartId = trade.getSellerId();
        }
        else if(trade.getSellerId().equals(myId)){
            isSeller = true;
            counterpartId = trade.getPurchaserId();
        }

        FirebaseDatabase.getInstance().getReference().child("customer").child(counterpartId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counterpart = new Customer(dataSnapshot);
                counterpartRating = counterpart.getCreditRating();
                counterpartEvaluationCount = counterpart.getEvaluationCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
