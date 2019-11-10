package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.List;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class RecyclerViewTradeAdapter extends RecyclerView.Adapter<RecyclerViewTradeAdapter.TradeViewHolder> {
    LayoutInflater inflater;
    List<Trade> modelList;

    public RecyclerViewTradeAdapter(Context context, List<Trade> list) {
        inflater = LayoutInflater.from(context);
        modelList = list;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trade_item, parent, false);
        return new TradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TradeViewHolder holder, int position) {
        holder.bindData(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class TradeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bookTitleTextView;
        TextView bookPriceTextView;
        TextView sellerNameTextView;

        public TradeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            bookTitleTextView = itemView.findViewById(R.id.item_book_title);
            bookPriceTextView = itemView.findViewById(R.id.item_book_price);
            sellerNameTextView = itemView.findViewById(R.id.item_seller_name);
        }

        public void bindData(Trade object) {
            imageView.setBackgroundResource(R.drawable.bookimag);
            bookTitleTextView.setText(object.getBook().getTitle());
            bookPriceTextView.setText(String.valueOf(object.getBook().getPrice()));
            sellerNameTextView.setText(object.getSeller().getName());
        }
    }

    //RecyclerView에 TouchListener 설정 함수
     public static void setTouchListener(final Context context, Activity activity, RecyclerView recyclerView) {
        RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Toast toast = Toast.makeText(context, "RowClick!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.item_button_edit, R.id.item_button_delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        if (viewID == R.id.item_button_edit) {
                            Toast toast = Toast.makeText(context, "Edit!", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (viewID == R.id.item_button_delete) {
                            Toast toast = Toast.makeText(context, "Delete!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }
}
