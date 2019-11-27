package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class SellFragment extends Fragment {
    Context context;
    RecyclerView sellRecyclerView;
    RecyclerViewTradeAdapter_Sell sellAdapter;
    FirebaseCommunicator firebaseCommunicator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sell, container, false);
        this.context = root.getContext();

//       테스트
        Button btn = root.findViewById(R.id.book_info_test_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new ssu.ssu.huncheckwhatssu.utilClass.Book("123", "123", "테스트", null, "작가입니디.",
                        123, "길벗", "20161012", "asnkdasnksadklndas", null);

                BookState bookState = new BookState(BookState.bookState.GOOD, BookState.bookState.BEST, BookState.bookState.BAD, BookState.bookState.WORST, BookState.bookState.GOOD, BookState.bookState.BEST);
                book.setBookState(bookState);
                String seller = firebaseCommunicator.getUserPath();
                String purchaser = firebaseCommunicator.getUserPath();
                Trade trade = new Trade(book, seller, purchaser, Trade.TradeState.WAIT, "여기가 어딜까", Calendar.getInstance());

                Intent intent = new Intent(context, BookInfoActivity.class);

                intent.putExtra("BookInfoType", "BOOK_INFO_TRADE_DETAIL");
                intent.putExtra("book_info_trade_detail", trade);
                startActivity(intent);
            }
        });

        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(1).setChecked(true);

        Button add = (Button) root.findViewById(R.id.AddSellBook);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), NaverBookSearchActivity.class);
                //  in.putExtra("start","Add BookSell");
                startActivity(in);
                //sellList.add();
            }
        });

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        sellRecyclerView = root.findViewById(R.id.sell_list);
        sellRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseCommunicator = new FirebaseCommunicator(FirebaseCommunicator.WhichRecyclerView.sellRecyclerView);
        firebaseCommunicator.setRecyclerView(this.getContext(), this.getActivity(), sellRecyclerView, FirebaseCommunicator.WhichRecyclerView.sellRecyclerView);

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        sellAdapter = new RecyclerViewTradeAdapter_Sell(this.getContext(), firebaseCommunicator.getSellTradeListVector(), sellRecyclerView);
        sellRecyclerView.setAdapter(sellAdapter);
        sellAdapter.setSwipeable(this.getContext(), this.getActivity(),this, sellRecyclerView);

        return root;
    }

    //Edit 눌러서 EditSell Activity에서 복귀
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        String act = intent.getStringExtra("activity");
        if(act != null) {
            if (act.equals("EditSell")) {
                Trade trade = intent.getParcelableExtra("trade");
                int position = intent.getIntExtra("position", -1);
                firebaseCommunicator.getSellTradeListVector().get(position).Copy(trade);
                sellRecyclerView.getAdapter().notifyItemChanged(position);
                firebaseCommunicator.editTrade(trade);
            }
        }
    }

}