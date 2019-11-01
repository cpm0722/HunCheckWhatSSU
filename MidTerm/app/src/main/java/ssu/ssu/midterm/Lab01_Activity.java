package ssu.ssu.midterm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Lab01_Activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout rootLinear = new LinearLayout(this);
        rootLinear.setOrientation(LinearLayout.VERTICAL);
        ContentFrameLayout.LayoutParams rootLp = new ContentFrameLayout.LayoutParams(ContentFrameLayout.LayoutParams.MATCH_PARENT, ContentFrameLayout.LayoutParams.MATCH_PARENT);

        LinearLayout textLinear = new LinearLayout(this);
        textLinear.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rootLinear.addView(textLinear, textLp);

        TextView textView = new TextView(this);
        textView.setText("INPUT NAME!");
        textView.setTextAlignment(textView.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewLp.weight=1;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "xmas.ttf");
        textView.setTypeface(typeface);
        textLinear.addView(textView, textViewLp);

        EditText editText = new EditText(this);
        editText.setGravity(textView.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams editTextLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextLp.weight=1;
        textLinear.addView(editText, editTextLp);

        Button btn = new Button(this);
        btn.setText("저장하기");
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rootLinear.addView(btn, btnLp);

        setContentView(rootLinear, rootLp);

    }
}
