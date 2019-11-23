package ssu.ssu.huncheckwhatssu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static ssu.ssu.huncheckwhatssu.utilClass.BookState.bookState.GOOD;

public class TradeFragment extends Fragment {
    RecyclerViewTradeAdapter_Trade ongoingAdapter;
    RecyclerViewTradeAdapter_Trade doneAdapter;
    RecyclerView ongoingRecyclerView, doneRecyclerView;
    FirebaseCommunicator ongoingFirebase, doneFirebase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_trade, container, false);

        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        final Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(2).setChecked(true);

        /*편집 시작*/

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        ongoingFirebase = new FirebaseCommunicator(FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView);
        String seller = ongoingFirebase.getUserPath();
        ongoingRecyclerView = root.findViewById(R.id.trade_ongoing_list) ;
        ongoingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;
        ongoingFirebase.setRecyclerView(this.getContext(), this.getActivity(), ongoingRecyclerView, FirebaseCommunicator.WhichRecyclerView.ongoingRecyclerView);



        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        ongoingAdapter = new RecyclerViewTradeAdapter_Trade(this.getContext(), ongoingFirebase.getOngoingTradeListVector(), ongoingRecyclerView) ;
        ongoingAdapter.setSwipeable(this.getContext(), this.getActivity(), ongoingRecyclerView);
        ongoingRecyclerView.setAdapter(ongoingAdapter);





        /*거래진행중인 아이템개수 보여주기 위해서*/
        final TextView ongoingCountTrade=root.findViewById(R.id.ongoing_count);
        ongoingCountTrade.setText(""+ongoingAdapter.getItemCount()+" 건");


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        doneFirebase = new FirebaseCommunicator(FirebaseCommunicator.WhichRecyclerView.doneRecyclerView);
        doneRecyclerView = root.findViewById(R.id.trade_done_list) ;
        doneRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;
        doneFirebase.setRecyclerView(this.getContext(), this.getActivity(), doneRecyclerView, FirebaseCommunicator.WhichRecyclerView.doneRecyclerView);

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        doneAdapter = new RecyclerViewTradeAdapter_Trade(this.getContext(), doneFirebase.getDoneTradeListVector(), doneRecyclerView) ;
        doneAdapter.setSwipeable(this.getContext(), this.getActivity(), doneRecyclerView);
        doneRecyclerView.setAdapter(doneAdapter);


        /*거래진행중인 아이템개수 보여주기 위해서*/
        final TextView doneCountTrade=root.findViewById(R.id.done_count);
        doneCountTrade.setText(""+doneAdapter.getItemCount()+" 건");

        /*리사이클러뷰에 구분선 넣기
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyleritem_line));
        ongoingRecyclerView.addItemDecoration(dividerItemDecoration);
        doneRecyclerView.addItemDecoration(dividerItemDecoration);*/

        return root;
    }



}