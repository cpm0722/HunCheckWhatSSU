package ssu.ssu.huncheckwhatssu.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import ssu.ssu.huncheckwhatssu.R;

public class DBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "HunCheckWhatSSUDB";
    public final static int DATABASE_VERSION = 1;
    private Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDBTable(db);

    }

    private void createDBTable(SQLiteDatabase db) {
        String sql = "create table tb_college(id integer primary key autoincrement, name)";
        db.execSQL(sql);
        sql = "create table tb_department(id integer primary key autoincrement, college_id integer, name)";
        db.execSQL(sql);
        sql = "create table tb_subject(id integer primary key autoincrement, department_id integer, subject_num, name, professor, target)";
        db.execSQL(sql);

        loadData(R.raw.college_data, "tb_college", "insert into tb_college values(?,?)");
        loadData(R.raw.department_data, "tb_department", "insert into tb_department values(?,?,?)");
        loadData(R.raw.subject_data, "tb_subject", "insert into tb_subject values(?,?,?,?,?,?)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != DATABASE_VERSION) {
            db.execSQL("drop table tb_college");
            db.execSQL("drop table tb_department");
            db.execSQL("drop table tb_subject");
            createDBTable(db);
        }
    }

    public String[] loadData(int rawResource, String tb_name, String sql) {
        InputStream stream = context.getResources().openRawResource(rawResource);

        if (stream != null) {
            InputStreamReader ism = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(ism);
            String line;
            String dataList[]=null;

            getWritableDatabase().execSQL("delete from " + tb_name);

            try {
                while ((line = br.readLine()) != null) {
                    dataList = line.split(",");

                    getWritableDatabase().execSQL(sql, dataList);
                }
            } catch (Exception e) {}
        }

        return null;
    }
}
