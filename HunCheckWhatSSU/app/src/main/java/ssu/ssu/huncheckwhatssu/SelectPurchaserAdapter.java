package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SelectPurchaserAdapter extends RecyclerView.Adapter<SelectPurchaserAdapter.MyViewHolder> {

    private Vector<Customer> purchasers;
    private LayoutInflater layoutInflater;

    public SelectPurchaserAdapter(Context context,Vector<Customer> purchasers){
        this.purchasers = purchasers;
        this.layoutInflater = LayoutInflater.from(context);
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
    }

    @Override
    public int getItemCount() {
        return purchasers.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView creditReteText;
        public TextView addressText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameText = itemView.findViewById(R.id.purchaser_name_text);
            this.creditReteText = itemView.findViewById(R.id.purchaser_credit_rate_text);
            this.addressText = itemView.findViewById(R.id.purchaser_address_text);
        }
    }
}
