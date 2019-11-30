package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ssu.ssu.huncheckwhatssu.DB.DBData;
import ssu.ssu.huncheckwhatssu.DB.DBHelper;
import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.BookState;
import ssu.ssu.huncheckwhatssu.utilClass.Customer;
import ssu.ssu.huncheckwhatssu.utilClass.Trade;

import static android.widget.Toast.LENGTH_SHORT;
import static ssu.ssu.huncheckwhatssu.utilClass.BookState.bookState.BEST;

public class EditSell extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Trade trade;
    Customer customer;
    Book book;

    Button btn_modifyback2Sell;
    Button btn_back2Sell;
//Book
    TextView activity_book_edit_title;
    ImageView activity_book_edit_coverImg;
    TextView activity_book_edit_authorText;
    TextView activity_book_edit_publisherText;
    TextView activity_book_edit_publicationDateText;
    TextView activity_book_edit_bookCostText;
    EditText activity_book_edit_bookSellingpriceText;

    // BookState
    RadioGroup activity_book_edit_state01;
    RadioGroup activity_book_edit_state02;
    RadioGroup activity_book_edit_state03;
    RadioGroup activity_book_edit_state04;
    RadioGroup activity_book_edit_state05;
    RadioGroup activity_book_edit_state06;
    //스피너
    FirebaseCommunicator firebaseCommunicator;
    Spinner edit_college_sp;
    Spinner edit_department_sp;
    Spinner edit_subject_sp;
    ArrayList<DBData> editcollegeData;
    ArrayList<DBData> editdepartmentData;
    ArrayList<DBData> editsubjectData;

    Intent resultIntent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sell);

        /*recyclerview의 각 아이템 받기*/
        Intent intent = getIntent();
        trade = intent.getParcelableExtra("editTrade");
        book = trade.getBook();
        position = intent.getIntExtra("position", -1);



        //return할 Intent
        resultIntent = new Intent(this, MainActivity.class);


        findviews();
        makeclickable();
        editcollegeData = new ArrayList<>();
        editdepartmentData = new ArrayList<>();
        editsubjectData = new ArrayList<>();
        setSpinnerData(1,-1,-1);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getDataName(editcollegeData));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_college_sp.setAdapter(arrayAdapter);


        setdata(trade);

    }

    public void setdata(Trade trade) {
       /*기존 데이터 그대로 보여주기*/
        activity_book_edit_coverImg .getDrawable();
        activity_book_edit_title.setText(trade.getBook().getTitle());
        activity_book_edit_authorText .setText(trade.getBook().getAuthor());
        activity_book_edit_publisherText .setText(trade.getBook().getPublisher());
        activity_book_edit_publicationDateText .setText(trade.getBook().getPubDate());
        activity_book_edit_bookCostText .setText(""+trade.getBook().getOriginalPrice());
        checkdefaultradio();
        checkdefaultspinner();
        /*새로운 데이터 보여주기*/
        activity_book_edit_bookSellingpriceText.setText(""+trade.getSellingPrice());
        // BookState
        // activity_book_edit_state01.getCheckedRadioButtonId();라디오 그룹에서 선택된 라디오 버튼의 아이디를 반환
        //id.setChecked(true);:아이디.setChecked
        /*radioGroup.check(라디오버튼.getId());*/
        /*int getCheckedRadioButtonId()특정 라디오 버튼을 선택한 채로 초기화*/
    }

    private void checkdefaultspinner() {
       Log.d("helpme",""+trade.getBook().getDepartment_id()+" "+trade.getBook().getCollege_id()+" "+trade.getBook().getSubject_id());

        edit_department_sp.setSelection(Integer.parseInt(trade.getBook().getDepartment_id()));
       // edit_college_sp.setSelection(trade.getBook().getCollege_id());
       // edit_subject_sp.setSelection(trade.getBook().getSubject_id());
    }

    private void checkdefaultradio() {

        BookState bookstate=trade.getBook().getBookState();
        switch (bookstate.getBookState01()) {
            case WORST:
                activity_book_edit_state01.check(R.id.book_state_WORST1);
                break;
            case BAD:
                activity_book_edit_state01.check(R.id.book_state_BAD1);
                break;
            case GOOD:
                activity_book_edit_state01.check(R.id.book_state_GOOD1);
                break;
            case BEST:
                activity_book_edit_state01.check(R.id.book_state_BEST1);
                break;
        }
        switch (bookstate.getBookState02()) {
            case WORST:
                activity_book_edit_state02.check(R.id.book_state_WORST2);
                break;
            case BAD:
                activity_book_edit_state02.check(R.id.book_state_BAD2);
                break;
            case GOOD:
                activity_book_edit_state02.check(R.id.book_state_GOOD2);
                break;
            case BEST:
                activity_book_edit_state02.check(R.id.book_state_BEST2);
                break;
        }
        switch (bookstate.getBookState03()) {
            case WORST:
                activity_book_edit_state03.check(R.id.book_state_WORST3);
                break;
            case BAD:
                activity_book_edit_state03.check(R.id.book_state_BAD3);
                break;
            case GOOD:
                activity_book_edit_state03.check(R.id.book_state_GOOD3);
                break;
            case BEST:
                activity_book_edit_state03.check(R.id.book_state_BEST3);
                break;
        }
        switch (bookstate.getBookState04()) {
            case WORST:
                activity_book_edit_state04.check(R.id.book_state_WORST4);
                break;
            case BAD:
                activity_book_edit_state04.check(R.id.book_state_BAD4);
                break;
            case GOOD:
                activity_book_edit_state04.check(R.id.book_state_GOOD4);
                break;
            case BEST:
                activity_book_edit_state04.check(R.id.book_state_BEST4);
                break;
        }
        switch (bookstate.getBookState05()) {
            case WORST:
                activity_book_edit_state05.check(R.id.book_state_WORST5);
                break;
            case BAD:
                activity_book_edit_state05.check(R.id.book_state_BAD5);
                break;
            case GOOD:
                activity_book_edit_state05.check(R.id.book_state_GOOD5);
                break;
            case BEST:
                activity_book_edit_state05.check(R.id.book_state_BEST5);
                break;
        }
        switch (bookstate.getBookState06()) {
            case WORST:
                activity_book_edit_state06.check(R.id.book_state_WORST6);
                break;
            case BAD:
                activity_book_edit_state06.check(R.id.book_state_BAD6);
                break;
            case GOOD:
                activity_book_edit_state06.check(R.id.book_state_GOOD6);
                break;
            case BEST:
                activity_book_edit_state06.check(R.id.book_state_BEST6);
                break;
        }

    }

    private void findviews() {
        activity_book_edit_coverImg = (ImageView) findViewById(R.id.activity_book_edit_coverImg);
        activity_book_edit_title = (TextView) findViewById(R.id.activity_book_edit_title);
        activity_book_edit_authorText = (TextView)findViewById(R.id.activity_book_edit_authorText);
        activity_book_edit_publisherText = (TextView)findViewById(R.id.activity_book_edit_publisherText);
        activity_book_edit_publicationDateText = (TextView)findViewById(R.id.activity_book_edit_publicationDateText);
        activity_book_edit_bookCostText =(TextView)findViewById(R.id.activity_book_edit_bookCostText);

        activity_book_edit_bookSellingpriceText=(EditText)findViewById(R.id.activity_book_edit_bookSellingpriceText);
        // BookState
        activity_book_edit_state01 = (RadioGroup) findViewById(R.id.book_state_line);
        activity_book_edit_state02 = (RadioGroup)findViewById(R.id.book_state_write);
        activity_book_edit_state03 = (RadioGroup)findViewById(R.id.book_state_cover);
        activity_book_edit_state04 = (RadioGroup)findViewById(R.id.book_state_name);
        activity_book_edit_state05 = (RadioGroup)findViewById(R.id.book_state_color);
        activity_book_edit_state06 = (RadioGroup)findViewById(R.id.book_state_pagegone);

        btn_back2Sell = (Button)findViewById(R.id.back2Sell);
        btn_modifyback2Sell=(Button)findViewById(R.id.modifyback2Sell);


        /*스피너*/

        edit_college_sp=(Spinner)findViewById(R.id.activity_edit_college_sp);
        edit_department_sp=(Spinner)findViewById(R.id.activity_edit_department_sp);
        edit_subject_sp=(Spinner)findViewById(R.id.activity_edit_subject_sp);
    }

    private void makeclickable(){
        /*수정 삭제 버튼 달기*/
        btn_back2Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultIntent.putExtra("activity", "EditSell_CANCLED");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        btn_modifyback2Sell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(activity_book_edit_bookSellingpriceText.getText().toString().length() <= 1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "판매가격을 입력하세요", LENGTH_SHORT);
                    toast.show();
                }
                else if(edit_college_sp.getSelectedItemPosition() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "대학을 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
                else if(edit_department_sp.getSelectedItemPosition() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "전공을 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
               /* else if(edit_subject_sp.getSelectedItemPosition() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "강의를 선택하세요", LENGTH_SHORT);
                    toast.show();
                }*/
                else if((activity_book_edit_state01.getCheckedRadioButtonId() == -1) || (activity_book_edit_state02.getCheckedRadioButtonId() == -1 ) || (activity_book_edit_state03.getCheckedRadioButtonId() == -1) || (activity_book_edit_state04.getCheckedRadioButtonId() == -1) || (activity_book_edit_state05.getCheckedRadioButtonId() == -1) || (activity_book_edit_state06.getCheckedRadioButtonId() == -1)){
                    Toast toast = Toast.makeText(getApplicationContext(), "책상태를 선택하세요", LENGTH_SHORT);
                    toast.show();
                }
                else {
                    trade.setSellingPrice(Integer.parseInt((activity_book_edit_bookSellingpriceText.getText()).toString()));
                    trade.getBook().setCollege_id(""+edit_college_sp.getSelectedItemId());
                    trade.getBook().setDepartment_id(""+edit_department_sp.getSelectedItemId());
                    trade.getBook().setSubject_id(""+edit_subject_sp.getSelectedItemId());
                    //return할 Intent 생성
                    resultIntent.putExtra("activity", "EditSell");
                    resultIntent.putExtra("editTrade", trade);
                    resultIntent.putExtra("position", position);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        /*라디오그룹에 setOnCheckedChangeListener달기*/
        activity_book_edit_state01.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST1:
                        book.getBookState().setBookState01(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD1:
                        book.getBookState().setBookState01(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD1:
                        book.getBookState().setBookState01(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST1:
                        book.getBookState().setBookState01(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state02.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST2:
                        book.getBookState().setBookState02(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD2:
                        book.getBookState().setBookState02(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD2:
                        book.getBookState().setBookState02(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST2:
                        book.getBookState().setBookState02(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state03.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST3:
                        book.getBookState().setBookState03(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD3:
                        book.getBookState().setBookState03(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD3:
                        book.getBookState().setBookState03(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST3:
                        book.getBookState().setBookState03(BookState.bookState.WORST);
                        break;
                }
            }
        });

        activity_book_edit_state04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST4:
                        book.getBookState().setBookState04(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD4:
                        book.getBookState().setBookState04(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD4:
                        book.getBookState().setBookState04(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST4:
                        book.getBookState().setBookState04(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state05.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST5:
                        book.getBookState().setBookState05(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD5:
                        book.getBookState().setBookState05(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD5:
                        book.getBookState().setBookState05(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST5:
                        book.getBookState().setBookState05(BookState.bookState.WORST);
                        break;
                }
            }
        });
        activity_book_edit_state06.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.book_state_BEST6:
                        book.getBookState().setBookState06(BookState.bookState.BEST);
                        break;
                    case R.id.book_state_GOOD6:
                        book.getBookState().setBookState06(BookState.bookState.GOOD);
                        break;
                    case R.id.book_state_BAD6:
                        book.getBookState().setBookState06(BookState.bookState.BAD);
                        break;
                    case R.id.book_state_WORST6:
                        book.getBookState().setBookState06(BookState.bookState.WORST);
                        break;
                }
            }
        });

        edit_college_sp.setOnItemSelectedListener(this);
        edit_department_sp.setOnItemSelectedListener(this);
        edit_subject_sp.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == edit_college_sp) {
            if (position == 0) {
                setSpinnerData(2, 0, -1);
                Log.d("ej", "[1-1]" + edit_college_sp.getSelectedItemPosition());
            } else {
                setSpinnerData(2, editcollegeData.get(position).getKey(), -1);
                Log.d("ej", "[1-2]" + edit_college_sp.getSelectedItemPosition());

            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getDataName(editdepartmentData));

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_department_sp.setAdapter(arrayAdapter);
            Log.d("ej", "onItemSelected: z2" + parent.getItemAtPosition(position));

        } else if (parent == edit_department_sp) {
            if (position == 0) {
                setSpinnerData(3, -1, 0);
            } else {
                setSpinnerData(3, -1, editdepartmentData.get(position).getKey());
                Log.d("ej", "onItemSelected(department): z3" + editdepartmentData.get(position).getKey());

            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getDataName(editsubjectData));

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_subject_sp.setAdapter(arrayAdapter);
            Log.d("ej", "onItemSelected: z4 " + parent.getItemAtPosition(position));

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private ArrayList<String> getDataName(ArrayList<DBData> dbData) {
        ArrayList<String> arrayList = new ArrayList();

        for (int i = 0; i < dbData.size(); i++) {
            if (dbData.get(i).getAnother() != null)
                arrayList.add("(" + dbData.get(i).getAnother()[0] + ")" +dbData.get(i).getName());
            else arrayList.add(dbData.get(i).getName());
        }

        return arrayList;
    }

    private void setSpinnerData(int edit_spin_switch,int edit_college_id,int edit_department_id){
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (edit_spin_switch == 1) {
            editcollegeData.clear();
            editdepartmentData.clear();
            editsubjectData.clear();

            cursor = db.rawQuery("select * from tb_college", null);

            editcollegeData.add(new DBData(-1, -1, "전체", null));

            while (cursor.moveToNext()) {
                editcollegeData.add(new DBData(cursor.getInt(0), -1, cursor.getString(1), null));
            }
        }

        if (edit_spin_switch == 2) {
            editdepartmentData.clear();
            editsubjectData.clear();

            if (edit_college_id == 0)
                cursor = db.rawQuery("select * from tb_department", null);
            else
                cursor = db.rawQuery("select * from tb_department where college_id = ?", new String[]{(edit_college_id)+""});

            editdepartmentData.add(new DBData(-1, -1, "전체", null));

            while (cursor.moveToNext()) {
                editdepartmentData.add(new DBData(cursor.getInt(0), cursor.getInt(1) , cursor.getString(2), null));
            }
        }

        if (edit_spin_switch == 3) {
            editsubjectData.clear();

            if (edit_department_id == 0)
                cursor = db.rawQuery("select * from tb_subject", null);
            else
                cursor = db.rawQuery("select * from tb_subject where department_id = ?", new String[]{(edit_department_id)+""});

            editsubjectData.add(new DBData(-1, -1, "전체", null));

            while (cursor.moveToNext()) {
                editsubjectData.add(new DBData(cursor.getInt(0), cursor.getInt(1),
                        cursor.getString(3), new String[]{cursor.getString(2), cursor.getString(4), cursor.getString(5)}));
            }
        }

        db.close();
    }

}
