package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import ssu.ssu.huncheckwhatssu.DB.DBData;
import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static java.lang.Integer.parseInt;

public class RecyclerViewTradeAdapter_Trade extends RecyclerView.Adapter<RecyclerViewTradeAdapter_Trade.TradeViewHolder> {
    LayoutInflater inflater;
    Vector<Trade> modelList;
    RecyclerView recyclerView;
    TextView countView;
    DBHelper dbHelper;
    RecyclerViewTradeAdapter_Trade ongoing;
    RecyclerViewTradeAdapter_Trade done;


    public Vector<Trade> getTrades() {
        return modelList;
    }

    public RecyclerViewTradeAdapter_Trade(Context context, Vector<Trade> vector, RecyclerView recyclerView, TextView countView) {
        this.inflater = LayoutInflater.from(context);
        this.modelList = vector;
        this.recyclerView = recyclerView;
        this.countView = countView;
        dbHelper = new DBHelper(context);
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

    public TextView getCountView(){return countView;}

    public void setAnotherAdapter(FirebaseCommunicator.WhichRecyclerView whichRecyclerView, RecyclerViewTradeAdapter_Trade another){
        if(whichRecyclerView == FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView){
            ongoing = this;
            done = another;
        }
        else {
            ongoing = another;
            done = this;
        }
        return;
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
            if (object.getSeller().getName() == null) {
                object.setSeller(new Customer(object.getSellerId()));
                object.getSeller().setCustomerDataFromUID(recyclerView.getAdapter());
            }
            book_image.setBackgroundResource(R.drawable.bookimag);
            book_title.setText(object.getBook().getTitle());
            original_price.setText(String.valueOf(object.getBook().getOriginalPrice()));
            seller_name.setText(object.getSeller().getName());


            book_author.setText(object.getBook().getAuthor());
            book_publisher.setText(object.getBook().getPublisher());
            selling_price.setText(String.valueOf(object.getSellingPrice()));
            seller_credit.setText(object.getSeller().getCreditRating()+"");
            DBHelper dbHelper = new DBHelper(inflater.getContext());
            book_category.setText(dbHelper.getFullCategoryText(object.getBook()));
            countView.setText(new Integer(getItemCount()) + " 건");
            //seller_credit.setText((int)object.getSeller().getCreditRating());

            //   book_image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.bookimag,null))
        }
    }


    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public void setSwipeable(final Context context, Activity activity, final RecyclerView recyclerView) {
        final RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Trade trade = ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().get(position);

                        Toast toast = Toast.makeText(context, "RowClick! " + trade.getBook().getTitle(), Toast.LENGTH_SHORT);
                        toast.show();
                        // recyclerView.getAdapter().notifyItemChanged(position);

                        /*여기에 클릭 하는 거 처리해야함*/

                        Intent intent = new Intent(context, BookInfoActivity.class);
                        intent.putExtra("BookInfoType", "BOOK_INFO_TRADE_DETAIL");
                        intent.putExtra("book_info_trade_detail", trade);
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
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        final Trade trade = ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().get(position);
                        if (viewID == R.id.item_button_delete) {
                            Toast toast = Toast.makeText(context, "Delete! " + trade.getBook().getTitle(), Toast.LENGTH_SHORT);
                            toast.show();
                            if (trade.getTradeState() == Trade.TradeState.WAIT) {
                                /*판매 등록 삭제*/
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("판매 종료");
                                alert.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        /*삭제되는 코드 넣기*///  recyclerView.remove(position);
                                        Toast toast = Toast.makeText(context, "판매종료", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                                alert.setNegativeButton("유지", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast toast = Toast.makeText(context, "유지", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                                alert.show();
                            } else if (trade.getTradeState() == Trade.TradeState.COMPLETE) {
                                /*만약 상태가 거래완료이면
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("거래 내역 삭제");
                                alert.setMessage("삭제시, 거래 내역을 볼 수 없습니다.");
                                alert.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                         recyclerView.remove(position);
                                        Toast toast=Toast.makeText(context,"내역삭제함",Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast toast=Toast.makeText(context,"취소함",Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                                alert.show();
                                */
                            } else if (trade.getTradeState() == Trade.TradeState.PRECONTRACT) {
                                /*만약, 상태가 거래진행중이면*/
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("거래 취소");
                                alert.setMessage("정말로 거래 취소 하시겠습니까?\n 신용도에 영향을 줍니다.");
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseCommunicator.tradeCancel(trade.getTradeId(), trade.getSellerId(), trade.getPurchaserId());
                                        ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().remove(position);
                                        /*삭제되는 코드 넣기*///  recyclerView.remove(position);
                                        recyclerView.getAdapter().notifyItemRemoved(position);
                                        recyclerView.getAdapter().notifyDataSetChanged();
                                        countView.setText(recyclerView.getAdapter().getItemCount() + " 건");
                                        Toast toast = Toast.makeText(context, "거래취소함", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        //거래 완료로 TradeState 수정
                                        trade.setTradeState(Trade.TradeState.COMPLETE);
                                        FirebaseCommunicator.editTrade(trade);
                                        //ongoing에서 제거
                                        ongoing.getTrades().remove(position);
                                        ongoing.notifyItemChanged(position);
                                        ongoing.getCountView().setText(ongoing.getItemCount() + " 건");
                                        //done에 추가
                                        if(done != null){
                                            done.getTrades().add(trade);
                                            done.notifyDataSetChanged();
                                            done.getCountView().setText(done.getItemCount() + " 건");
                                        }
                                        Toast toast = Toast.makeText(context, "취소함", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                                alert.show();
                            }

                        }
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }

}
