package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class TradeFragment extends Fragment {
    RecyclerViewTradeAdapter_Trade ongoingAdapter;
    RecyclerViewTradeAdapter_Trade doneAdapter;
    RecyclerView ongoingRecyclerView, doneRecyclerView;
    FirebaseCommunicator firebase;

    TextView ongoingCountTrade;
    TextView doneCountTrade;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_trade, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("HunCheckWhatSSU-거래중");

        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        final Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(2).setChecked(true);


        ongoingCountTrade=root.findViewById(R.id.ongoing_count);
        doneCountTrade=root.findViewById(R.id.done_count);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        firebase = new FirebaseCommunicator(FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView);
        ongoingRecyclerView = root.findViewById(R.id.trade_ongoing_list) ;
        ongoingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;
        firebase.setRecyclerView(this.getContext(), this.getActivity(), ongoingRecyclerView, FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView);

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        ongoingAdapter = new RecyclerViewTradeAdapter_Trade(this.getContext(), firebase.getOngoingTradeListVector(), ongoingRecyclerView, ongoingCountTrade, firebase, FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView) ;
        ongoingAdapter.setSwipeable(this.getContext(), this.getActivity(), this, ongoingRecyclerView);
        ongoingRecyclerView.setAdapter(ongoingAdapter);


        /*거래진행중인 아이템개수 보여주기 위해서*/
        ongoingCountTrade.setText(""+ongoingAdapter.getItemCount()+" 건");

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        doneRecyclerView = root.findViewById(R.id.trade_done_list) ;
        doneRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        doneAdapter = new RecyclerViewTradeAdapter_Trade(this.getContext(), firebase.getDoneTradeListVector(), doneRecyclerView, doneCountTrade, firebase, FirebaseCommunicator.WhichRecyclerView.doneRecyclerView);
        doneAdapter.setSwipeable(this.getContext(), this.getActivity(), this, doneRecyclerView);
        doneRecyclerView.setAdapter(doneAdapter);

        /*거래진행중인 아이템개수 보여주기 위해서*/
        doneCountTrade.setText(""+doneAdapter.getItemCount()+" 건");

        ongoingAdapter.notifyDataSetChanged();
        doneAdapter.notifyDataSetChanged();

        ongoingAdapter.setAnotherAdapter(FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView, doneAdapter);
        doneAdapter.setAnotherAdapter(FirebaseCommunicator.WhichRecyclerView.doneRecyclerView, ongoingAdapter);

        /*리사이클러뷰에 구분선 넣기
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyleritem_line));
        ongoingRecyclerView.addItemDecoration(dividerItemDecoration);
        doneRecyclerView.addItemDecoration(dividerItemDecoration);*/

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == Activity.RESULT_OK) {
            int position = intent.getIntExtra("position", -1);
            if (position != -1) {
                final Trade trade = intent.getParcelableExtra("trade");
                ongoingAdapter.MoveFromOngoingToDone(position, trade);
                FirebaseDatabase.getInstance().getReference().child("customer").child(trade.getSellerId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer seller = new Customer(dataSnapshot);
                        int sellerTradeCount = seller.getTradeCount();
                        sellerTradeCount++;
                        FirebaseDatabase.getInstance().getReference().child("customer").child(trade.getSellerId()).child("tradeCount").setValue(sellerTradeCount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                FirebaseDatabase.getInstance().getReference().child("customer").child(trade.getPurchaserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer purchaser = new Customer(dataSnapshot);
                        int purchaserTradeCount = purchaser.getTradeCount();
                        purchaserTradeCount++;
                        FirebaseDatabase.getInstance().getReference().child("customer").child(trade.getPurchaserId()).child("tradeCount").setValue(purchaserTradeCount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}