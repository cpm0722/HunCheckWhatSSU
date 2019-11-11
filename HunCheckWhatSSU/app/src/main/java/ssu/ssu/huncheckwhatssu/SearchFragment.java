package ssu.ssu.huncheckwhatssu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchFragment extends Fragment implements View.OnClickListener {
    Button bookInfoBtn;
    Button addBtn;
    FirebaseCommunicator firebase;

    private RecyclerView recyclerView;
    private RecyclerViewTradeAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);


        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(0).setChecked(true);

        //RecyclerView
        recyclerView = root.findViewById(R.id.search_fragment_recycler_view);
        adapter = new RecyclerViewTradeAdapter(this.getContext(), new ArrayList<Trade>());
        recyclerView.setAdapter(adapter);
        RecyclerViewTradeAdapter.setSwipeable(this.getContext(), this.getActivity(), recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecyclerViewTradeAdapter.SetRefresh((SwipeRefreshLayout)root.findViewById(R.id.swipe_fragment_search));



        //FirebaseCommunicator 생성
        firebase = new FirebaseCommunicator();
        //FirebaseCommunicator에 RecyclerView 설정
        firebase.setRecyclerView(this.getContext(), this.getActivity(), recyclerView);

        bookInfoBtn = root.findViewById(R.id.book_info_btn);
        bookInfoBtn.setOnClickListener(this);

        //TEST용 Firebase 추가 버튼
        addBtn = root.findViewById(R.id.test_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Book book = new Book("testISBN10", "testISBN13", "testTitle", "testImage", "testAuthor", 15000, "testPublisher", "testPubDate", "testDescription", new BookState());
                Customer seller = new Customer("testId", "testName", "testPhoneNumber", "testAdress", (float) 1.0);
                Trade trade = new Trade(book, seller);
                firebase.uploadTrade(trade);
            }
        });

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == bookInfoBtn) {
            Intent intent = new Intent(this.getActivity(), BookInfoActivity.class);
            this.getActivity().startActivity(intent);
        }
    }
}