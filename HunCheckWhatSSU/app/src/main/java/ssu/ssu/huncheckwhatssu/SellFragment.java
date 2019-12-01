package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;
import java.util.Vector;

import androidx.annotation.NonNull;
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
        
        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(1).setChecked(true);

        Button add=(Button)root.findViewById(R.id.AddSellBook);
        add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
          Intent in=new Intent(getActivity(),NaverBookSearchActivity.class);
        //  in.putExtra("start","Add BookSell");
          startActivity(in);
          //sellList.add();
         }
        });

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        sellRecyclerView = root.findViewById(R.id.sell_list) ;
        sellRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;

        firebaseCommunicator = new FirebaseCommunicator(FirebaseCommunicator.WhichRecyclerView.sellRecyclerView);
        firebaseCommunicator.setRecyclerView(this.getContext(), this.getActivity(), sellRecyclerView, FirebaseCommunicator.WhichRecyclerView.sellRecyclerView);

        // 리사이클러뷰에 RecyclerViewAdapter1 객체 지정.
        sellAdapter = new RecyclerViewTradeAdapter_Sell(this.getContext(), firebaseCommunicator.getSellTradeListVector(), sellRecyclerView) ;
        sellRecyclerView.setAdapter(sellAdapter);
        sellAdapter.setSwipeable(this.getContext(), this.getActivity(), sellRecyclerView);

        return root;
    }
}