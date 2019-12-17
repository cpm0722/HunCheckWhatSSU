package ssu.ssu.huncheckwhatssu;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.HashMap;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);


         sharedPreferences = getSharedPreferences("HunCheckWhatSSU",MODE_PRIVATE);
         if(!sharedPreferences.getBoolean("initial",false))
              initialSetting();
         if(sharedPreferences.getBoolean("notification",true))
            startNotificationListener();

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

    protected void initialSetting(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String path = user.getDisplayName()+"_"+user.getUid();
        FirebaseDatabase.getInstance().getReference().child("customer").child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChildren()){
                    HashMap<String, Object> initial = new HashMap<>();
                    initial.put("Uid",path);
                    initial.put("Name",user.getDisplayName());
                    initial.put("NickName", user.getDisplayName());
                    initial.put("CreditRating", 3.0);
                    initial.put("tradeCount", 0);
                    initial.put("evaluationCount", 0);
                    initial.put("cancelCount", 0);
                    FirebaseDatabase.getInstance().getReference().child("customer").child(path).updateChildren(initial);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("initial",true);
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void startNotificationListener(){
        boolean startByNoti = getIntent().getBooleanExtra("Started By Notification",false);
        if(!startByNoti) {
            Log.d("YYYCCC","왜들어옴?");
            startService(new Intent(this, NotificationService.class));
        }
    }

    @Override
    public void onBackPressed() {
        final Activity root = this;
        new AlertDialog.Builder(root)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.dialog_exit_title)
                .setMessage(R.string.dialog_exit_question)
                .setPositiveButton(R.string.dialog_exit_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        root.finish();
                    }

                })
                .setNegativeButton(R.string.dialog_exit_no, null)
                .show();
    }
}
