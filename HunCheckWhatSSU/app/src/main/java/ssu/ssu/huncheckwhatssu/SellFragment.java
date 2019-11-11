package ssu.ssu.huncheckwhatssu;

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

public class SellFragment extends Fragment {
       public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sell, container, false);

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

        ArrayList<Book>list =new ArrayList<Book>();
        list.add(new Book("자료구조","15000","김승주"));
        list.add(new Book("컴퓨터구조","20000","김승주"));
        list.add(new Book("Pro Git","170000","김승주"));
        list.add(new Book("알고리즘","20000","김승주"));
        list.add(new Book("리눅스","230000","김승주"));

        RecyclerView recyclerView = root.findViewById(R.id.sellList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;

        RecyclerViewAdapterSell adapter = new RecyclerViewAdapterSell(list) ;
        recyclerView.setAdapter(adapter) ;

        return root;
    }
}