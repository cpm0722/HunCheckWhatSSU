package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class SettingPersonalInfo extends AppCompatActivity implements View.OnClickListener{

    private Button setImageBtn;
    private Button cancelBtn;
    private Button saveBtn;

    private EditText editName;
    private EditText editNickName;
    private EditText editMajor;
    private EditText editGrade;
    private EditText editContactAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personal);

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

        //initializaing();
    }

    private void initializaing() {
        Customer customer;
        String name, nickName, major, contactAddress, grade, address;

        //사용자 정보를 텍스트 박스에 초기화하는 작업

        /*name = customer.getName();
        nickName = customer.getNickName();
        major = customer.getMajor();
        grade = customer.getGrade();
        contactAddress = customer.getPhoneNumber();
        address = customer.getAddress();

        editName.setText(name);
        editNickName.setText(nickName);
        editMajor.setText(major);
        editGrade.setText(grade);
        editContactAddress.setText(contactAddress);*/
    }

    @Override
    public void onClick(View view) {
        if(view == setImageBtn){

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
        Customer customer = new Customer();

        //customer 객체에 입력받은 텍스트를 저장하는 작업
        FirebaseUser me = FirebaseAuth.getInstance().getCurrentUser();
        customer.setId(me.getDisplayName()+"_"+me.getUid());
        customer.setPhoneNumber("01000000000");
        customer.setCreditRating(3.5);
        customer.setName(editName.getText().toString());
       // customer.setNickName(editNickName.getText());
        //customer.setMajor(editMajor.getText());
       // customer.setGrdae(editGrade.getText());
        customer.setAddress(editContactAddress.getText().toString());

        FirebaseCommunicator temp = new FirebaseCommunicator();
        temp.upLoadCustomer(customer);
    }

}
