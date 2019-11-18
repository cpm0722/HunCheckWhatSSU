package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SettingPersonalInfo extends AppCompatActivity implements View.OnClickListener{

    Customer myInfo;
    FirebaseCommunicator firebaseCommunicator;
    private Button setImageBtn;
    private Button cancelBtn;
    private Button saveBtn;

    private EditText editName;
    private EditText editNickName;
    private EditText editMajor;
    private EditText editGrade;
    private EditText editContactAddress;
    private EditText editAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personal);


        getId();
        firebaseCommunicator = new FirebaseCommunicator();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseCommunicator.setForSettingPersonalInfo(new FirebaseCommunicator.forSettingPersonalInfo() {
            @Override
            public void onSettingListener(Customer customer) {
                myInfo = customer;
                Log.d("YECHAN", myInfo.getName());
                initializaing();
            }
        });
        firebaseCommunicator.getCustomerById(user.getDisplayName() + "_" + user.getUid());
        //initializaing();
    }

    private void getId(){
        setImageBtn = (Button)findViewById(R.id.setting_personal_image_btn);
        cancelBtn = (Button)findViewById(R.id.setting_personal_info_cancel_btn);
        saveBtn = (Button)findViewById(R.id.setting_personal_info_save_btn);
        setImageBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        editName = (EditText)findViewById(R.id.setting_personal_name_edit);
        editNickName = (EditText)findViewById(R.id.setting_personal_nickname_edit);
        editMajor = (EditText)findViewById(R.id.setting_personal_major);
        editGrade = (EditText)findViewById(R.id.setting_personal_grade_edit);
        editContactAddress = (EditText)findViewById(R.id.setting_personal_contactAddress_edit);
        editAddress = findViewById(R.id.setting_personal_address_edit);
    }

    private void initializaing() {

        String name, nickName, major, phoneNumber, address;
        int grade;
        //사용자 정보를 텍스트 박스에 초기화하는 작업

        name = myInfo.getName();
        nickName = myInfo.getNickName();
        major = myInfo.getMajor();
        grade = myInfo.getGrade();
        phoneNumber = myInfo.getPhoneNumber();
        address = myInfo.getAddress();

        editName.setText(name);
        editNickName.setText(nickName);
        editMajor.setText(major);
        editGrade.setText(Integer.toString(grade));
        editContactAddress.setText(phoneNumber);
        editAddress.setText(address);
    }

    @Override
    public void onClick(View view) {
        if(view == setImageBtn){
            if(myInfo == null) {
                Log.d("YECHAN","myinfo 값이 안들어옴");
            }
        }
        else if(view == cancelBtn){
            finish();
        }
        else if(view == saveBtn){
            savePersonalInfo();     //수정된 정보 저장하는 메소드
            Toast.makeText(this,"저장되었습니다",Toast.LENGTH_SHORT);
            finish();
        }
    }

    private void savePersonalInfo() {

        //customer 객체에 입력받은 텍스트를 저장하는 작업
        myInfo.setName(editName.getText().toString());
        myInfo.setNickName(editNickName.getText().toString());
        myInfo.setMajor(editMajor.getText().toString());
        myInfo.setGrade(Integer.parseInt(editGrade.getText().toString()));
        myInfo.setPhoneNumber(editContactAddress.getText().toString());
        myInfo.setAddress(editAddress.getText().toString());

        firebaseCommunicator.upLoadCustomer(myInfo);
    }
}
