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

import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewTradeAdapter_Trade extends RecyclerView.Adapter<RecyclerViewTradeAdapter_Trade.TradeViewHolder> {
    LayoutInflater inflater;
    Vector<Trade> modelList;
    RecyclerView recyclerView;


    public Vector<Trade> getTrades() {
        return modelList;
    }

    public RecyclerViewTradeAdapter_Trade(Context context, Vector<Trade> vector, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        modelList = vector;
        this.recyclerView = recyclerView;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trade_item_fragment_trade, parent, false);
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

    public class TradeViewHolder extends RecyclerView.ViewHolder {
        ImageView book_image;
        TextView book_title;
        TextView original_price;
        TextView selling_price;
        TextView seller_name;
        TextView book_category;
        TextView book_author;
        TextView book_publisher;
        TextView seller_credit;

        public TradeViewHolder(View itemView) {
            super(itemView);
            book_image = itemView.findViewById(R.id.item_image);
            book_title = itemView.findViewById(R.id.item_book_title);
            original_price = itemView.findViewById(R.id.item_book_original_price);
            selling_price = itemView.findViewById(R.id.item_book_selling_price);
            seller_name = itemView.findViewById(R.id.item_seller_name);
            book_category = itemView.findViewById(R.id.item_book_category);
            book_author = itemView.findViewById(R.id.item_book_author);
            book_publisher = itemView.findViewById(R.id.item_book_publisher);
            seller_credit = itemView.findViewById(R.id.item_seller_credit);

        }

        public void bindData(Trade object) {
            if(object.getSeller().getName() == null) {
                object.setSeller(new Customer(object.getSellerId()));
                object.getSeller().setCustomerDataFromUID(recyclerView.getAdapter());
            }
            book_image.setBackgroundResource(R.drawable.bookimag);
            book_title.setText(object.getBook().getTitle());
            original_price.setText(String.valueOf(object.getBook().getOriginalPrice()));
            seller_name.setText(object.getSeller().getName());
            book_category.setText(object.getBook().getCollege_id());
            book_author.setText(object.getBook().getAuthor());
            book_publisher.setText(object.getBook().getPublisher());
            selling_price.setText(String.valueOf(object.getBook().getSellingPrice()));
            seller_credit.setText("신뢰");
            //seller_credit.setText((int)object.getSeller().getCreditRating());

            //   book_image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.bookimag,null))
        }
    }

    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public static void setSwipeable(final Context context, Activity activity, final RecyclerView recyclerView) {
        final RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Trade trade = ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().get(position);
                        Toast toast = Toast.makeText(context, "RowClick! " + trade.getBook().getTitle(), Toast.LENGTH_SHORT);
                        toast.show();
                        recyclerView.getAdapter().notifyItemChanged(position);
                        /*여기에 클릭 하는 거 처리해야함*/

                        Intent intent = new Intent(context, BookInfoActivity.class);

                        context.startActivity(intent);

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        Log.d(TAG, "Indepent");
                    }
                })
                .setSwipeOptionViews(R.id.item_button_delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        Trade trade = ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().get(position);
                        if (viewID == R.id.item_button_delete) {
                            Toast toast = Toast.makeText(context, "Delete! " + trade.getBook().getTitle(), Toast.LENGTH_SHORT);
                            toast.show();
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }

}
