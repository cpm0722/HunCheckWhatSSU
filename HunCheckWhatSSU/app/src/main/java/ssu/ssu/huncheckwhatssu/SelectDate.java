package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class SelectDate extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button back=(Button)findViewById(R.id.notselectdate);
        Button selctdate=(Button)findViewById(R.id.selectdate);

        setContentView(R.layout.activity_select_date);
    }

}
