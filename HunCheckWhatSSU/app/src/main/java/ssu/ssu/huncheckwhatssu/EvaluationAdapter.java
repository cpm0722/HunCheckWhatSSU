package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.MyViewHolder> {

    private static String TAG = "DEBUG!";
    private Vector<Trade> trades;
    private LayoutInflater layoutInflater;
    private String id;
    private FirebaseCommunicator firebaseCommunicator;

    public interface OnItemClickListener{
        void onItemClicked(View v, int position);
    }
    public EvaluationAdapter(Context context, Vector<Trade> doneTrades, String id){
        this.trades = doneTrades;

        Log.d(TAG, "EvaluationAdapter: last" + trades.size());
        this.layoutInflater = LayoutInflater.from(context);
        this.id = id;
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
        if(trades.get(position).getSellerId().equals(id)){
            holder.isSell.setText("판매");
            counterpartId = this.trades.get(position).getPurchaserId();
            holder.rating.setText(this.trades.get(position).getSellerRate() + "");
            holder.comment.setText(this.trades.get(position).getSellerComment());
        }
        else{
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

    @Override
    public int getItemCount() {
        return trades.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
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
}
