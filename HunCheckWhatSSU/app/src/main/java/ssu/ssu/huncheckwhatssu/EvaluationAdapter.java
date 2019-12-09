package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.MyViewHolder> {

    private static String TAG = "DEBUG!";
    private Vector<Trade> trades;
    private LayoutInflater layoutInflater;
    private String id;
    private boolean isMyId;

    public interface OnItemClickListener {
        void onItemClicked(View v, int position);
    }

    public EvaluationAdapter(Context context, Vector<Trade> doneTrades, String id, boolean isMyId) {
        this.trades = doneTrades;

        Log.d(TAG, "EvaluationAdapter: last" + trades.size());
        this.layoutInflater = LayoutInflater.from(context);
        this.id = id;
        this.isMyId = isMyId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customerview = layoutInflater.inflate(R.layout.item_evaluation, null);
        MyViewHolder myViewHolder = new MyViewHolder(customerview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        String counterpartId = null;
        if (trades.get(position).getSellerId().equals(id)) {
            holder.isSell.setText("판매");
            counterpartId = this.trades.get(position).getPurchaserId();
            holder.rating.setText(this.trades.get(position).getSellerRate() + "");
            holder.comment.setText(this.trades.get(position).getSellerComment());
        } else {
            holder.isSell.setText("구매");
            counterpartId = this.trades.get(position).getSellerId();
            holder.rating.setText(this.trades.get(position).getPurchaserRate() + "");
            holder.comment.setText(this.trades.get(position).getPurchaserComment());
        }
        FirebaseDatabase.getInstance().getReference().child("customer").child(counterpartId).child("NickName").addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.fromId.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }));
    }

    public Vector<Trade> getTrades() {
        return trades;
    }

    @Override
    public int getItemCount() {
        return trades.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView isSell;
        public TextView fromId;
        public TextView rating;
        public TextView comment;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.isSell = itemView.findViewById(R.id.item_evaluation_isSell);
            this.fromId = itemView.findViewById(R.id.item_evaluate_id);
            this.rating = itemView.findViewById(R.id.item_evaluation_text_rating);
            this.comment = itemView.findViewById(R.id.item_evaluation_text_comment);
        }
    }

    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public void setSwipeable(final Context context, final Activity activity, final RecyclerView recyclerView) {
        final RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                    }
                })
                .setSwipeOptionViews(R.id.item_evaluation_button_report)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        final Trade trade = ((EvaluationAdapter) (recyclerView.getAdapter())).getTrades().get(position);
                        if (viewID == R.id.item_evaluation_button_report) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("신고");
                            alert.setMessage("정말로 해당 평가에 대해 신고를 하시겠습니까? 허위 신고는 제재 사유입니다.");
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference().child("report").child(trade.getTradeId()).setValue(FirebaseCommunicator.getMyId());
                                    Toast.makeText(context, "신고 접수 완료! 관리자가 해당 신고에 대해 검토합니다.", Toast.LENGTH_LONG).show();
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
        if(isMyId)
            recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }

}
