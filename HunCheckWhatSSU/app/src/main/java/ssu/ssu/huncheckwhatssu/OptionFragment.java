package ssu.ssu.huncheckwhatssu;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

public class OptionFragment extends Fragment implements View.OnClickListener {

    private Button seeMyInfoBtn;
    private Button seeMyEvaluationBtn;
    private Button setPersonalInfoBtn;
    private Button setNotificationBtn;
    private Button customerContactAddressBtn;
    private Button logoutBtn;
    private Switch settingNotificationRequestSwitch;
    private SharedPreferences sharedPreferences;

    private AlertDialog setNotificationDialog;
    private AlertDialog showCustomerSupportContactDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_option, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("HunCheckWhatSSU-설정");
        sharedPreferences = getActivity().getSharedPreferences("HunCheckWhatSSU",Context.MODE_PRIVATE);

        //BackButton Pressed 시 NavigationBottom Menu Selected 변경
        Fragment navHostFragment = this.getActivity().getSupportFragmentManager().getFragments().get(0);
        BottomNavigationView navView = navHostFragment.getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.getItem(3).setChecked(true);

        seeMyInfoBtn = root.findViewById(R.id.see_my_info);
        seeMyEvaluationBtn = root.findViewById(R.id.see_my_evaluation);
        setPersonalInfoBtn = (Button)root.findViewById(R.id.setting_Personal_Info_Btn);
        setNotificationBtn = (Button)root.findViewById(R.id.setting_notification_btn);
        customerContactAddressBtn= (Button)root.findViewById(R.id.customer_support_center_btn);
        logoutBtn = root.findViewById(R.id.logout_btn);
        seeMyInfoBtn.setOnClickListener(this);
        seeMyEvaluationBtn.setOnClickListener(this);
        setPersonalInfoBtn.setOnClickListener(this);
        setNotificationBtn.setOnClickListener(this);
        customerContactAddressBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if(view == setPersonalInfoBtn){
            Intent intent = new Intent(getActivity().getApplicationContext(),SettingPersonalInfo.class);
            intent.putExtra("Edit",true);
            startActivity(intent);
        }
        else if(view == seeMyEvaluationBtn){
            Intent intent = new Intent(getActivity().getApplicationContext(), EvaluationActivity.class);
            intent.putExtra("id", FirebaseCommunicator.getMyId());
            startActivity(intent);
        }
        else if(view == setNotificationBtn) {
            setNotification();
        }
        else if(view == customerContactAddressBtn){
            showCustomerSupportContactAddress();
        }
        else if(view == seeMyInfoBtn){
            Intent intent = new Intent(getActivity().getApplicationContext(),SettingPersonalInfo.class);
            intent.putExtra("Edit",false);
            startActivity(intent);
        }
        else if(view == logoutBtn){
            Toast.makeText(getContext(), "Sign Out", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            return;
        }
    }
    private void setNotification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View notificationDialogView = inflater.inflate(R.layout.dialog_setting_notification,null);
        settingNotificationRequestSwitch = notificationDialogView.findViewById(R.id.setting_notification_request_switch);
        if(sharedPreferences.getBoolean("notification",true)){
            settingNotificationRequestSwitch.setChecked(true);
        }
        else
            settingNotificationRequestSwitch.setChecked(false);
        builder.setTitle("알림 설정");
        builder.setView(notificationDialogView);
        builder.setNegativeButton("취소",null);
        builder.setPositiveButton("저장",dialogListner);
        setNotificationDialog = builder.create();
        setNotificationDialog.show();
    }
    private void showCustomerSupportContactAddress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("고객센터 연락처");
        builder.setMessage("010-0000-0000");
        builder.setPositiveButton("확인",null);
        showCustomerSupportContactDialog = builder.create();
        showCustomerSupportContactDialog.show();
    }
    DialogInterface.OnClickListener dialogListner = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if(dialogInterface == setNotificationDialog&&i==DialogInterface.BUTTON_POSITIVE){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notification",settingNotificationRequestSwitch.isChecked());
                editor.commit();
            }
        }
    };
}