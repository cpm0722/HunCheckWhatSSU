package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
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

import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewTradeAdapter_Search extends RecyclerView.Adapter<RecyclerViewTradeAdapter_Search.TradeViewHolder> {
    LayoutInflater inflater;
    static List<Trade> modelList;
    static custom_RefreshListener custom_RefreshListener;

    interface custom_RefreshListener {
        void onRefreshListener();
    }

    public void setOnRefreshListener(custom_RefreshListener onRefreshListener) {
        this.custom_RefreshListener = onRefreshListener;
    }

    public RecyclerViewTradeAdapter_Search(Context context, List<Trade> list) {
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
        TextView bookAuthorTextView;
        TextView bookCategoryTextView;
        TextView bookPublisherTextView;
        TextView bookSellingPriceTextView;
        TextView sellerCreditTextView;

        public TradeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            bookTitleTextView = itemView.findViewById(R.id.item_book_title);
            bookPriceTextView = itemView.findViewById(R.id.item_book_original_price);

            bookPriceTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            sellerNameTextView = itemView.findViewById(R.id.item_seller_name);
            bookAuthorTextView = itemView.findViewById(R.id.item_book_author);
            bookCategoryTextView= itemView.findViewById(R.id.item_book_category);
            bookPublisherTextView = itemView.findViewById(R.id.item_book_publisher);
            bookSellingPriceTextView = itemView.findViewById(R.id.item_book_selling_price);
            sellerCreditTextView = itemView.findViewById(R.id.item_seller_credit);

        }

        public void bindData(Trade object) {
            if (object == null) return;

            Log.d("JS", "bindData: " + object.toString());

            imageView.setBackgroundResource(R.drawable.bookimag);
            bookTitleTextView.setText(object.getBook().getTitle());
            bookPriceTextView.setText(String.valueOf(object.getBook().getOriginal_Price()));
            bookSellingPriceTextView.setText(String.valueOf(object.getBook().getSellingPrice()));
            bookPublisherTextView.setText(String.valueOf(object.getBook().getPublisher()));

            DBHelper dbHelper = new DBHelper(inflater.getContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            StringBuilder sb = new StringBuilder();

            Cursor cursor = db.rawQuery("select * from tb_college where id = ?", new String[]{object.getBook().getCollege_id()});

            while (cursor.moveToNext()) {
                sb.append(cursor.getString(1));
            }

            sb.append(">");

            cursor = db.rawQuery("select * from tb_department where id = ?", new String[]{object.getBook().getDepartment_id()});

            while (cursor.moveToNext()) {
                sb.append(cursor.getString(2));
            }

            sb.append(">");

            cursor = db.rawQuery("select * from tb_subject where id = ?", new String[]{object.getBook().getSubject_id()});

            while (cursor.moveToNext()) {
                sb.append(cursor.getString(3));
            }

            bookCategoryTextView.setText(sb.toString());
            bookAuthorTextView.setText(String.valueOf(object.getBook().getAuthor()));
            sellerCreditTextView.setText(String.format("%.2f", object.getSeller()._getCreditRating()));
            sellerNameTextView.setText(object.getSeller()._getName());
        }
    }

    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public static void setSwipeable(final Context context, Activity activity, RecyclerView recyclerView) {
        RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Trade trade = modelList.get(position);

                        Intent intent = new Intent(context, BookInfoActivity.class);
                        intent.putExtra("BookInfoType", "BOOK_INFO_DEFAULT");
                        intent.putExtra("book_info_default_data", trade);

                        context.startActivity(intent);

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

                // search refresh
                if (RecyclerViewTradeAdapter_Search.custom_RefreshListener != null) {
                    custom_RefreshListener.onRefreshListener();
                }
            }
        });
    }
}