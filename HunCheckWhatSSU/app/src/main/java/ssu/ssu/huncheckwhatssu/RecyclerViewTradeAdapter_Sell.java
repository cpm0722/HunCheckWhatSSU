package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewTradeAdapter_Sell extends RecyclerView.Adapter<RecyclerViewTradeAdapter_Sell.TradeViewHolder> {
    LayoutInflater inflater;
    Vector<Trade> modelVector;
    FirebaseCommunicator firebaseCommunicator;

    public RecyclerViewTradeAdapter_Sell(Context context, FirebaseCommunicator firebaseCommunicator, Vector<Trade> vector) {
        inflater = LayoutInflater.from(context);
        modelVector = vector;
        this.firebaseCommunicator = firebaseCommunicator;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trade_item, parent, false);
        return new TradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TradeViewHolder holder, int position) {
        holder.bindData(modelVector.get(position));
    }

    public Vector<Trade> getTrades(){ return modelVector;}

    @Override
    public int getItemCount() {
        return modelVector.size();
    }

    class TradeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bookTitleTextView;
        TextView sellerNameTextView;
        TextView original_price;
        TextView selling_price;

        public TradeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            bookTitleTextView = itemView.findViewById(R.id.item_book_title);
            sellerNameTextView = itemView.findViewById(R.id.item_seller_name);
            original_price = itemView.findViewById(R.id.item_book_original_price);
            selling_price=itemView.findViewById(R.id.item_book_selling_price);
        }

        public void bindData(Trade object) {
            imageView.setBackgroundResource(R.drawable.bookimag);
            bookTitleTextView.setText(object.getBook().getTitle());
            sellerNameTextView.setText(object.getSellerId());
            original_price.setText(String.valueOf(object.getBook().getSellingPrice()));
            selling_price.setText(String.valueOf(object.getBook().getSellingPrice()));
        }
    }

    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public static void setSwipeable(final Context context, Activity activity, final RecyclerView recyclerView) {
        RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Trade trade = ((RecyclerViewTradeAdapter_Sell)(recyclerView.getAdapter())).getTrades().get(position);
                        Toast toast = Toast.makeText(context, "RowClick! " + trade.getBook().getTitle(), Toast.LENGTH_SHORT);
                        toast.show();
                        recyclerView.getAdapter().notifyItemChanged(position);
                        Intent intent=new Intent(context,BookInfoActivity.class);
                        context.startActivity(intent);

                        /*여기에 액티비티로 전달하는 기능이 구현되있어야함.*/
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        Toast toast = Toast.makeText(context, "IndependentViewID", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .setSwipeOptionViews(R.id.item_button_edit, R.id.item_button_delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        Trade trade = ((RecyclerViewTradeAdapter_Sell)(recyclerView.getAdapter())).getTrades().get(position);
                        if (viewID == R.id.item_button_edit) {
                            Toast toast = Toast.makeText(context, "Edit! " + trade.getBook().getTitle(), Toast.LENGTH_SHORT);
                            toast.show();
                            recyclerView.getAdapter().notifyItemChanged(position);
                        } else if (viewID == R.id.item_button_delete) {
                            Toast toast = Toast.makeText(context, "Delete! " + trade.getBook().getTitle(), Toast.LENGTH_SHORT);
                            toast.show();
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }

    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 불가능하게)
    public static void setNonSwipeable(final Context context, Activity activity, RecyclerView recyclerView) {
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
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }

    public static void SetRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //새로 고침할 작업 나중에 추가하기
                swipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, "recyclerview: swipe&Refresh");

            }
        });
    }
}