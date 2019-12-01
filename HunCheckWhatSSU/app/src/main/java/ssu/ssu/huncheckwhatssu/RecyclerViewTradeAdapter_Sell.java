package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class RecyclerViewTradeAdapter_Sell extends RecyclerView.Adapter<RecyclerViewTradeAdapter_Sell.TradeViewHolder> {
    LayoutInflater inflater;
    Vector<Trade> modelVector;
    RecyclerView recyclerView;
    TextView countView;

    public RecyclerViewTradeAdapter_Sell(Context context,  Vector<Trade> vector, RecyclerView recyclerView, TextView countView) {
        this.inflater = LayoutInflater.from(context);
        this.modelVector = vector;
        this.recyclerView = recyclerView;
        this.countView = countView;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trade_item_fragment_sell, parent, false);
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
        TextView originalPriceTextView;
        TextView sellingPriceTextView;
        TextView bookCategoryTextView;
        TextView bookAuthorTextView;
        TextView bookPublisherTextView;
        TextView sellerCreditTextView;

        public TradeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            bookTitleTextView = itemView.findViewById(R.id.item_book_title);
            sellerNameTextView = itemView.findViewById(R.id.item_seller_name);
            originalPriceTextView = itemView.findViewById(R.id.item_book_original_price);
            sellingPriceTextView =itemView.findViewById(R.id.item_book_selling_price);
            bookCategoryTextView = itemView.findViewById(R.id.item_book_category);
            bookAuthorTextView = itemView.findViewById(R.id.item_book_author);
            bookPublisherTextView = itemView.findViewById(R.id.item_book_publisher);
            sellerCreditTextView = itemView.findViewById(R.id.item_seller_credit);
        }

        public void bindData(Trade object) {
            if(object.getSeller().getName() == null) {
                object.setSeller(new Customer(object.getSellerId()));
                object.getSeller().setCustomerDataFromUID(recyclerView.getAdapter());
            }
            imageView.setBackgroundResource(R.drawable.bookimag);
            bookTitleTextView.setText(object.getBook().getTitle());
            sellerNameTextView.setText(object.getSeller().getName());
            originalPriceTextView.setText(String.valueOf(object.getBook().getOriginalPrice()));
            sellingPriceTextView.setText(String.valueOf(object.getSellingPrice()));
            bookAuthorTextView.setText(object.getBook().getAuthor());
            bookPublisherTextView.setText(object.getBook().getPublisher());
            sellerCreditTextView.setText("위험");
            DBHelper dbHelper = new DBHelper(inflater.getContext());
            bookCategoryTextView.setText(dbHelper.getFullCategoryText(object.getBook()));
            countView.setText(getItemCount() + " 건");
        }
    }

    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public void setSwipeable(final Context context, final Activity activity, final Fragment fragment, final RecyclerView recyclerView) {
        RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Trade trade = ((RecyclerViewTradeAdapter_Sell)(recyclerView.getAdapter())).getTrades().get(position);
                        recyclerView.getAdapter().notifyItemChanged(position);
                        Intent intent=new Intent(context,BookInfoActivity.class);
                        intent.putExtra("BookInfoType","BOOK_INFO_TRADE_DETAIL");
                        intent.putExtra("book_info_trade_detail", trade);
                        context.startActivity(intent);
                        /*여기에 액티비티로 전달하는 기능이 구현되있어야함.*/
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        Toast toast = Toast.makeText(context, "IndependentViewID", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .setSwipeOptionViews(R.id.item_button_notification, R.id.item_button_edit, R.id.item_button_delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        final Trade trade = ((RecyclerViewTradeAdapter_Sell)(recyclerView.getAdapter())).getTrades().get(position);
                        if (viewID == R.id.item_button_notification) {
                            Toast toast = Toast.makeText(activity, "구매요청!", Toast.LENGTH_SHORT);
                            toast.show();
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                        else if (viewID == R.id.item_button_edit) {
                            Intent intent=new Intent(context, EditSell.class);
                            intent.putExtra("activity", "SellFragment");
                            intent.putExtra("editTrade", trade);
                            intent.putExtra("position", position);
                            fragment.startActivityForResult(intent, 1);

                            recyclerView.getAdapter().notifyItemChanged(position);
                        } else if (viewID == R.id.item_button_delete) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("거래 삭제");
                            alert.setMessage("정말로 거래를 삭제 하시겠습니까?");
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseCommunicator.deleteTrade(trade);
                                    ((RecyclerViewTradeAdapter_Sell) (recyclerView.getAdapter())).getTrades().remove(position);
                                    recyclerView.getAdapter().notifyItemRemoved(position);
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                    countView.setText(recyclerView.getAdapter().getItemCount() + " 건");
                                    Toast toast = Toast.makeText(context, "거래삭제함", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                            alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    FirebaseCommunicator.tradePrecontract(trade.getTradeId(), trade.getSellerId(), "김승주_M3wdnkONA0cFMzXSqwt2dLLcfNI2");
                                    modelVector.remove(position);
                                    notifyItemRemoved(position);
                                    countView.setText(getItemCount() + " 건");
                                    Toast toast = Toast.makeText(context, "취소함", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                            alert.show();
                        }
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }

}