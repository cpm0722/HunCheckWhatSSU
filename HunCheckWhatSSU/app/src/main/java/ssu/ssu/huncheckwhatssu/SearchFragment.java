package ssu.ssu.huncheckwhatssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchFragment extends Fragment implements View.OnClickListener{
    Button bookInfoBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);


        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(0).setChecked(true);

        bookInfoBtn = root.findViewById(R.id.book_info_btn);
        bookInfoBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.getActivity(), BookInfoActivity.class);
        this.getActivity().startActivity(intent);
    }
}