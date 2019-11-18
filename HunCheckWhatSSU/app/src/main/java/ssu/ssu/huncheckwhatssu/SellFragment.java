package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class SellFragment extends Fragment {
       Context context;
       RecyclerView sellRecyclerView;
       RecyclerViewTradeAdapter_Search sellAdapter;

       public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sell, container, false);
        this.context = root.getContext();
//       테스트
        Button btn = root.findViewById(R.id.book_info_test_btn);

        btn.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v) {
          Book book = new ssu.ssu.huncheckwhatssu.utilClass.Book("123", "123", "테스트", null, "작가입니디.",
                  123, "길벗","20161012","asnkdasnksadklndas", null);

          BookState bookState = new BookState(BookState.bookState.GOOD, BookState.bookState.BEST,BookState.bookState.BAD,BookState.bookState.WORST,BookState.bookState.GOOD,BookState.bookState.BEST);
          book.setBookState(bookState);
          Customer seller = new Customer("1", "정지승","010-1010-1212","관악구 중앙동 양녕로", 5.0f);
          Customer purchaser = new Customer("2", "정지승2", "010-1234-1234", "상도동 상도로 상도리", 3.1f);
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

        Button add=(Button)root.findViewById(R.id.AddSellBook);
        add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
          Intent in=new Intent(getActivity(),AddBookActivity.class);
          in.putExtra("start","Add BookSell");
          startActivity(in);
         }
        });

        final ArrayList<Trade>sellList =new ArrayList<Trade>();
        Book book = new Book("testISBN10", "testISBN13", "sell", "testImage", "testAuthor", 10000, "testPublisher", "testPubDate", "testDescription", new BookState());
        Customer seller = new Customer("testId", "dms", "testPhoneNumber", "testAdress", (float) 1.0);
        sellList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "sell2", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        sellList.add(new Trade(book, seller));
        book = new Book("testISBN10", "testISBN13", "sell3", "testImage", "testAuthor", 300, "testPublisher", "testPubDate", "testDescription", new BookState());
        seller = new Customer("testId", "dagag", "testPhoneNumber", "testAdress", (float) 1.0);
        sellList.add(new Trade(book, seller));

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        sellRecyclerView = root.findViewById(R.id.sell_list) ;
        sellRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;
        RecyclerViewTradeAdapter_Search.setSwipeable(this.getContext(), this.getActivity(), sellRecyclerView);

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        sellAdapter = new RecyclerViewTradeAdapter_Search(this.getContext(), sellList) ;
        sellRecyclerView.setAdapter(sellAdapter);
        RecyclerViewTradeAdapter_Search.SetRefresh((SwipeRefreshLayout)root.findViewById(R.id.swipe_fragment_sell));

        return root;
    }
}