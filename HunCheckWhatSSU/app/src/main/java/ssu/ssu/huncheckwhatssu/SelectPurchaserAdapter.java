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

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SelectPurchaserAdapter extends RecyclerView.Adapter<SelectPurchaserAdapter.MyViewHolder> {

    private Vector<Customer> purchasers;
    private Vector<Boolean> isClicked;
    private LayoutInflater layoutInflater;
    private  OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(View v, int position);
    }
    public SelectPurchaserAdapter(Context context,Vector<Customer> purchasers,Vector<Boolean> isClicked){
        this.purchasers = purchasers;
        this.layoutInflater = LayoutInflater.from(context);
        this.isClicked = isClicked;
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
        holder.nameText.setText(this.purchasers.get(position).getName());
        holder.creditReteText.setText(Double.toString(this.purchasers.get(position).getCreditRating()));
        String address;
        if((address=this.purchasers.get(position).getAddress())!=null)
            holder.addressText.setText(address);
        if(isClicked.get(position)==true){
            holder.background.setBackgroundColor(Color.parseColor("#FFFF00"));
        }
        else{
            holder.background.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return purchasers.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView creditReteText;
        public TextView addressText;
        public LinearLayout background;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.background = itemView.findViewById(R.id.purchaser_request_item_background);
            this.nameText = itemView.findViewById(R.id.purchaser_name_text);
            this.creditReteText = itemView.findViewById(R.id.purchaser_credit_rate_text);
            this.addressText = itemView.findViewById(R.id.purchaser_address_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION&&onItemClickListener !=null){
                        onItemClickListener.onItemClicked(itemView,pos);

                    }
                }
            });
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
