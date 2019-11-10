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

public class SettingPersonalInfo extends AppCompatActivity implements View.OnClickListener, TextWatcher {

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

    }

    //텍스트 리스너 오버라이드 메소드
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
