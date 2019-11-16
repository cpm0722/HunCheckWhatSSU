package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
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

import java.util.List;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewTradeAdapter2 extends RecyclerView.Adapter<RecyclerViewTradeAdapter2.TradeViewHolder> {
    LayoutInflater inflater;
    static List<Trade> modelList;

    public RecyclerViewTradeAdapter2(Context context, List<Trade> list) {
        inflater = LayoutInflater.from(context);
        modelList = list;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trade_item_trade, parent, false);
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

    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public static void setSwipeable(final Context context, Activity activity, RecyclerView recyclerView) {
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
                        Log.d(TAG,"Indepent");
                    }
                })
                .setSwipeOptionViews(R.id.item_button_delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        if (viewID == R.id.item_button_delete) {
                            Toast toast = Toast.makeText(context, "Delete!", Toast.LENGTH_SHORT);
                            toast.show();
                            //삭제 기능 추가(현재는 그냥 삭제 나중에 DB삭제)
                            modelList.remove(position);
                            //notifyItemRemoved(position);
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
