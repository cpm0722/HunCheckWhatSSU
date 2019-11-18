package ssu.ssu.huncheckwhatssu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class TradeFragment extends Fragment {
    RecyclerViewTradeAdapter_Search ongoingAdapter, doneAdapter;
    RecyclerView ongoingRecyclerView, doneRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_trade, container, false);

        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        final Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(2).setChecked(true);

        //OnGoing RecyclerView
        final ArrayList<Trade>ongoingList =new ArrayList<Trade>();
        Book book = new Book("testISBN10", "testISBN13", "git", "testImage", "testAuthor", 10000, "testPublisher", "testPubDate", "testDescription", new BookState());
        Customer seller = new Customer("testId", "dms", "testPhoneNumber", "testAdress", (float) 1.0);
        ongoingList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "hi", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        ongoingList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "hi", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        ongoingList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "hi", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        ongoingList.add(new Trade(book, seller));

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        ongoingRecyclerView = root.findViewById(R.id.trade_ongoing_list) ;
        ongoingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;
        RecyclerViewTradeAdapter_Search.setSwipeable(this.getContext(), this.getActivity(), ongoingRecyclerView);

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        ongoingAdapter = new RecyclerViewTradeAdapter_Search(this.getContext(), ongoingList) ;
        ongoingRecyclerView.setAdapter(ongoingAdapter);
        RecyclerViewTradeAdapter_Search.SetRefresh((SwipeRefreshLayout)root.findViewById(R.id.swipe_fragment_trade_ongoing));

        /*거래진행중인 아이템개수 보여주기 위해서*/
        final TextView ongoingCountTrade=root.findViewById(R.id.counttrade);
        ongoingCountTrade.setText(""+ongoingAdapter.getItemCount()+" 건");


        //Done RecyclerView
        final ArrayList<Trade>doneList =new ArrayList<Trade>();
        book = new Book("testISBN10", "testISBN13", "git2", "testImage", "testAuthor", 10000, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dms", "testPhoneNumber", "testAdress", (float) 1.0);
        doneList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "hi2", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        doneList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "hi3", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        doneList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "hi4", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        doneList.add(new Trade(book, seller));

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        doneRecyclerView = root.findViewById(R.id.trade_done_list) ;
        doneRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        doneAdapter = new RecyclerViewTradeAdapter_Search(this.getContext(), doneList) ;
        doneRecyclerView.setAdapter(doneAdapter);
        RecyclerViewTradeAdapter_Search.setSwipeable(this.getContext(), this.getActivity(), doneRecyclerView);
        RecyclerViewTradeAdapter_Search.SetRefresh((SwipeRefreshLayout)root.findViewById(R.id.swipe_fragment_trade_done));

        /*거래진행중인 아이템개수 보여주기 위해서*/
        final TextView doneCountTrade=root.findViewById(R.id.counttrade);
        doneCountTrade.setText(""+ongoingAdapter.getItemCount()+" 건");

        /*리사이클러뷰에 구분선 넣기*/
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyleritem_line));
        ongoingRecyclerView.addItemDecoration(dividerItemDecoration);
        doneRecyclerView.addItemDecoration(dividerItemDecoration);
        return root;
    }



}