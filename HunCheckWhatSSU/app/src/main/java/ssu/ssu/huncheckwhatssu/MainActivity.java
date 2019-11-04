package ssu.ssu.huncheckwhatssu;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));
        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.navigation_search:
                                navController.navigate(R.id.fragment_search);
                                break;
                            case R.id.navigation_sell:
                                navController.navigate(R.id.fragment_sell);
                                break;
                            case R.id.navigation_trade:
                                navController.navigate(R.id.fragment_trade);
                                break;
                            case R.id.navigation_option:
                                navController.navigate(R.id.fragment_option);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

}
