package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.MyViewHolder> {

    private Vector<Trade> trades;
    private LayoutInflater layoutInflater;
    private String myId;

    public interface OnItemClickListener{
        void onItemClicked(View v, int position);
    }
    public EvaluationAdapter(Context context, Vector<Trade> trades){
        this.trades = trades;
        this.layoutInflater = LayoutInflater.from(context);
        myId = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "_" + FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customerview = layoutInflater.inflate(R.layout.customer_item_select_purchaser,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(customerview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(trades.get(position).getSellerId().equals(myId)){
            holder.isSell.setText("판매");
            holder.rating.setText(this.trades.get(position).getSeller().getNickName());
            //holder.rating.setText(this.trades.get(position).getSellerRating());
        }
        else{
            holder.isSell.setText("구매");
            holder.rating.setText(this.trades.get(position).getPurchaser().getNickName());
            //holder.rating.setText(this.trades.get(position).getPurchaserRating());
        }
    }

    @Override
    public int getItemCount() {
        return trades.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView isSell;
        public TextView rating;
        public TextView comment;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.isSell = itemView.findViewById(R.id.item_evaluation_isSell);
            this.rating = itemView.findViewById(R.id.item_evaluation_text_rating);
            this.comment = itemView.findViewById(R.id.item_evaluation_text_comment);
        }
    }
}
