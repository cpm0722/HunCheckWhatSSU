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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewTradeAdapter_Search extends RecyclerView.Adapter<RecyclerViewTradeAdapter_Search.TradeViewHolder> {
    LayoutInflater inflater;
    static List<Trade> modelList;

    public RecyclerViewTradeAdapter_Search(Context context, List<Trade> modelList) {
        inflater = LayoutInflater.from(context);
        this.modelList = modelList;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trade_item_search, parent, false);

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

    public class TradeViewHolder extends RecyclerView.ViewHolder{
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
            bookPriceTextView.setText(String.valueOf(object.getBook().getOriginalPrice()));
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
            sellerCreditTextView.setText(String.format("%.2f", object.getSeller().getCreditRating()));
            sellerNameTextView.setText(object.getSeller().getName());
        }
    }

}