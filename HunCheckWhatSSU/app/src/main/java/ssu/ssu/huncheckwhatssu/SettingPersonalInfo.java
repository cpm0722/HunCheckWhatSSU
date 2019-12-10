package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SettingPersonalInfo extends AppCompatActivity implements View.OnClickListener{

    Customer myInfo;
    FirebaseHelper firebaseHelper;

    private boolean EditMode;

    private Button cancelBtn;
    private Button saveBtn;

    private EditText editName;
    private EditText editNickName;
    private EditText editMajor;
    private EditText editGrade;
    private EditText editContactAddress;
    private EditText editAddress;

    private TextView creditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personal);
        getSupportActionBar().setTitle("HunCheckWhatSSU-개인정보 설정");

        getId();
        EditMode = getIntent().getBooleanExtra("Edit",false);
        if(!EditMode)
            seeMyInfoMode();
        firebaseHelper = new FirebaseHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseHelper.addCallBackListener(new FirebaseHelper.CallBackListener() {
            @Override
            public void afterGetCustomer(Customer customer) {
                myInfo = customer;
                initializaing();
            }

            @Override
            public void afterGetPurchaseRequestCount(int count) {

            }


        });
        firebaseHelper.getCustomerById(user.getDisplayName() + "_" + user.getUid());
    }

    private void getId(){

        cancelBtn = (Button)findViewById(R.id.setting_personal_info_cancel_btn);
        saveBtn = (Button)findViewById(R.id.setting_personal_info_save_btn);
        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        editName = (EditText)findViewById(R.id.setting_personal_name_edit);
        editName.setFocusableInTouchMode(false);
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

        if(name != null)
            editName.setText(name);
        if(nickName != null)
            editNickName.setText(nickName);
        if(major != null)
            editMajor.setText(major);
        if(grade != 0)
            editGrade.setText(Integer.toString(grade));
        if(phoneNumber != null)
            editContactAddress.setText(phoneNumber);
        if(address != null)
            editAddress.setText(address);
        if(!EditMode)
            creditText.setText(Double.toString(myInfo.getCreditRating()));
    }
    public void seeMyInfoMode(){
        getSupportActionBar().setTitle("HunCheckWhatSSU-내 정보");

        cancelBtn.setVisibility(View.GONE);
        saveBtn.setVisibility(View.GONE);

        editName.setFocusableInTouchMode(false);
        editAddress.setFocusableInTouchMode(false);
        editContactAddress.setFocusableInTouchMode(false);
        editGrade.setFocusableInTouchMode(false);
        editMajor.setFocusableInTouchMode(false);
        editNickName.setFocusableInTouchMode(false);

        findViewById(R.id.setting_personal_credit_layout).setVisibility(View.VISIBLE);
        creditText = findViewById(R.id.setting_personal_credit_text);
    }

    @Override
    public void onClick(View view) {
        if(view == cancelBtn){
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

        firebaseHelper.upLoadCustomer(myInfo);
    }
}
