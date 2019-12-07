package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.Vector;

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
    FirebaseCommunicator firebaseCommunicator;
    boolean isOngoingAdapter;


    public Vector<Trade> getTrades() {
        return modelList;
    }

    public RecyclerViewTradeAdapter_Trade(Context context, Vector<Trade> vector, RecyclerView recyclerView, TextView countView, FirebaseCommunicator firebaseCommunicator, FirebaseCommunicator.WhichRecyclerView whichRecyclerView) {
        this.inflater = LayoutInflater.from(context);
        if (whichRecyclerView == FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView) {
            isOngoingAdapter = true;
        } else if (whichRecyclerView == FirebaseCommunicator.WhichRecyclerView.doneRecyclerView) {
            isOngoingAdapter = false;
        }
        this.modelList = vector;
        this.recyclerView = recyclerView;
        this.countView = countView;
        this.firebaseCommunicator = firebaseCommunicator;
        dbHelper = new DBHelper(context);
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ;
        if(modelList.get(0).getTradeState()== Trade.TradeState.COMPLETE) {
            view = inflater.inflate(R.layout.trade_item_fragment_trade, parent, false);
            view.findViewById(R.id.item_button_addDateNplace).setVisibility(View.GONE);

        }
       else{// if(modelList.get(0).getTradeState()== Trade.TradeState.PRECONTRACT){
            view = inflater.inflate(R.layout.trade_item_fragment_trade, parent, false);
        }


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

    public TextView getCountView() {
        return countView;
    }

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

        ImageView deleteBtnImage;
        TextView deleteBtnText;

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
            deleteBtnImage = itemView.findViewById(R.id.item_trade_delete_image);
            deleteBtnText = itemView.findViewById(R.id.item_trade_delete_text);

        }

        public void bindData(Trade object) {
            if (object.getSeller().getName() == null) {
                object.setSeller(new Customer(object.getSellerId()));
                object.getSeller().setCustomerDataFromUID(recyclerView.getAdapter());
            }
            if (object.getBook().getImage() == null) {
                book_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.noimage));
            } else Glide.with(itemView).load(object.getBook().getImage()).into(book_image);
            book_title.setText(object.getBook().getTitle());
            original_price.setText(String.valueOf(object.getBook().getOriginalPrice()));
            seller_name.setText(object.getSeller().getName());


            book_author.setText(object.getBook().getAuthor());
            book_publisher.setText(object.getBook().getPublisher());
            selling_price.setText(String.valueOf(object.getSellingPrice()));
            seller_credit.setText(object.getSeller().getCreditRating() + "");
            DBHelper dbHelper = new DBHelper(inflater.getContext());
            book_category.setText(dbHelper.getFullCategoryText(object.getBook()));
            countView.setText(new Integer(getItemCount()) + " 건");

            if(!isOngoingAdapter) {
                deleteBtnImage.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.icon_star));
                deleteBtnImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                deleteBtnText.setText("Evaluate");
            }
        }
    }


    //RecyclerView에 TouchListener 설정 함수 (Swipe로 메뉴 출력 가능하게)
    public void setSwipeable(final Context context, final Activity activity, final Fragment fragment, final RecyclerView recyclerView) {
        final RecyclerTouchListener onTouchListener = new RecyclerTouchListener(activity, recyclerView);
        onTouchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Trade trade = ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().get(position);
                        Intent intent = new Intent(context, BookInfoActivity.class);
                        intent.putExtra("BookInfoType", "BOOK_INFO_TRADE_DETAIL");
                        intent.putExtra("fragment", "trade");
                        intent.putExtra("book_info_trade_detail", trade);
                        intent.putExtra("position", position);
                        fragment.startActivityForResult(intent, 1);

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        Log.d(TAG, "Indepent");
                    }
                })
                .setSwipeOptionViews(R.id.item_button_delete, R.id.item_button_addDateNplace)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        final Trade trade = ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().get(position);
                        if (viewID == R.id.item_button_delete) {
                            if (trade.getTradeState() == Trade.TradeState.PRECONTRACT) {
                                /*만약, 상태가 거래진행중이면*/
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("거래 취소");
                                alert.setMessage("정말로 거래 취소 하시겠습니까?\n 신용도에 영향을 줍니다.");
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        firebaseCommunicator.tradeCancel(trade.getTradeId(), trade.getSellerId(), trade.getPurchaserId());
                                        ((RecyclerViewTradeAdapter_Trade) (recyclerView.getAdapter())).getTrades().remove(position);
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
                                    }
                                });
                                alert.show();
                            } else if(trade.getTradeState() == Trade.TradeState.COMPLETE){
                                boolean isEvaluated = false;
                                //내가 판매자일 때
                                if(trade.getSellerId().equals(FirebaseCommunicator.getMyId())){
                                   if(trade.getPurchaserRate() != -1){
                                       isEvaluated = true;
                                   }
                                }
                                //내가 구매자일 때
                                else{
                                    if(trade.getSellerRate() != -1){
                                        isEvaluated = true;
                                    }
                                }
                                //평가가 완료된 경우
                                if(isEvaluated){
                                    Toast toast = Toast.makeText(context, "이미 평가가 완료된 항목입니다.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                //평가가 아직 안 된 경우
                                else {
                                    Intent intent = new Intent(context, Rating.class);
                                    intent.putExtra("trade", trade);
                                    context.startActivity(intent);
                                }
                            }
                            recyclerView.getAdapter().notifyItemRemoved(position);
                            recyclerView.getAdapter().notifyDataSetChanged();


                        }
                        else if(viewID == R.id.item_button_addDateNplace){

                            Toast toast = Toast.makeText(context, "Date/Place! " , Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
        return;
    }

    public void MoveFromOngoingToDone(int position, Trade trade){
        ongoing.getTrades().remove(position);
        ongoing.notifyItemChanged(position);
        ongoing.getCountView().setText(ongoing.getItemCount() + " 건");
        //done에 추가
        if (done != null) {
            done.getTrades().add(trade);
            done.notifyDataSetChanged();
            done.getCountView().setText(done.getItemCount() + " 건");
        }
    }

}