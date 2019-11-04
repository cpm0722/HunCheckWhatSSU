package ssu.ssu.huncheckwhatssu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class TradeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trade, container, false);

        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(2).setChecked(true);



        ArrayList<Book>list =new ArrayList<Book>();
        list.add(new Book("git","10000","dms"));
        list.add(new Book("hi","300","dagaja"));
        list.add(new Book("123hi","4300","dagajdfa"));
        list.add(new Book("dgfhi","2300","dagajdfdfa"));
        list.add(new Book("sfshi","2323300","dagajdfdfa"));
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = root.findViewById(R.id.trade_ongoing_list) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list) ;
        recyclerView.setAdapter(adapter) ;


        ArrayList<Book>list2 =new ArrayList<Book>();
        list2.add(new Book("hello","87","nnn"));
        list2.add(new Book("hi","54","aaa"));
        list2.add(new Book("bonjure","430","dxxxxfa"));
        list2.add(new Book("mosi","2300","dagadddddddddddddddddddddddddddddjdfdfa"));
        list2.add(new Book("asdf","23300","kdrefdfa"));
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView2 = root.findViewById(R.id.trade_done_list) ;
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext())) ;

        // 리사이클러뷰에 RecyclerViewAdapter2 객체 지정.
        RecyclerViewAdapter2 adapter2 = new RecyclerViewAdapter2(list2) ;
        recyclerView2.setAdapter(adapter2) ;


        return root;
    }
}