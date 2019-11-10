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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class SellFragment extends Fragment {
       Context context;

       public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sell, container, false);
        this.context = root.getContext();
//       테스트
        Button btn = root.findViewById(R.id.book_info_test_btn);

        btn.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v) {
          ssu.ssu.huncheckwhatssu.utilClass.Book book = new ssu.ssu.huncheckwhatssu.utilClass.Book("123", "123", "테스트", null, "작가입니디.",
                  123, "기르벗","20162510","asnkdasnksadklndas", null);
          Trade trade = new Trade(book, null, Trade.TradeState.WAIT);

          Intent intent = new Intent(context, BookInfoActivity.class);

          intent.putExtra("BookInfoType", "BOOK_INFO_DEFAULT");
          intent.putExtra("book_info_default_data", trade);

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

        ArrayList<BookSell>list =new ArrayList<BookSell>();
        list.add(new BookSell("자료구조","15000","김승주"));
        list.add(new BookSell("컴퓨터구조","20000","김승주"));
        list.add(new BookSell("Pro Git","170000","김승주"));
        list.add(new BookSell("알고리즘","20000","김승주"));
        list.add(new BookSell("리눅스","230000","김승주"));

        RecyclerView recyclerView = root.findViewById(R.id.sellList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;

        RecyclerViewAdapterSell adapter = new RecyclerViewAdapterSell(list) ;
        recyclerView.setAdapter(adapter) ;

        return root;
    }
}