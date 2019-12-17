package com.example.lab11;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyPlusMinusView extends View {
    Context context;
    // 증감 값
    int value;
    // 화면 출력 이미지
    Bitmap plusBitmap;
    Bitmap minusBitmap;
    // 이미지가 화면에 출력되는 좌표 정보
    Rect plusRectDst;
    Rect minusRectDst;

    // value 출력 문자열 색상
    int textColor;

    // Observer를 등록하기 위한 객체
    ArrayList<OnMyChangeListener> listeners;

    public MyPlusMinusView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public MyPlusMinusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public MyPlusMinusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.AT_MOST) {
            width = 700;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = 250;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // 플러스 아이콘이 터치된 거라면...
        if (plusRectDst.contains(x, y) && event.getAction() == MotionEvent.ACTION_DOWN) {
            // 데이터 변경
            value++;
            // 화면 갱신
            invalidate();
            for (OnMyChangeListener listener : listeners) {
            // observer에 데이터 전달
                listener.onChange(value);
            }
            return true;
        } else if (minusRectDst.contains(x, y) && event.getAction() == MotionEvent.ACTION_DOWN) {
            value--;
            invalidate();
            for (OnMyChangeListener listener : listeners) {
            // observer에 데이터 전달
                listener.onChange(value);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 화면 지우기
        canvas.drawColor(Color.alpha(Color.CYAN));

        // 이미지의 사각형 정보
        Rect plusRectSource = new Rect(0, 0, plusBitmap.getWidth(), plusBitmap.getHeight());
        Rect minusRectSource = new Rect(0, 0, minusBitmap.getWidth(), minusBitmap.getHeight());
        Paint paint=new Paint();

        // plus 이미지 그리기
        canvas.drawBitmap(plusBitmap, plusRectSource, plusRectDst, null);

        // value 문자열 그리기
                paint.setTextSize(80);
        paint.setColor(textColor);
        canvas.drawText(String.valueOf(value), 260, 150, paint);

        // minus 이미지 그리기
        canvas.drawBitmap(minusBitmap, minusRectSource, minusRectDst, null);
    }

    private void init(AttributeSet attrs) {
// 이미지 획득
        plusBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plus);
        minusBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.minus);
// 이미지 출력 사각형 좌표 정보 설정
        plusRectDst = new Rect(10, 10, 210, 210);
        minusRectDst = new Rect(400, 10, 600, 210);
// custom 속성값 획득
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);
            textColor = a.getColor(R.styleable.MyView_customTextColor, Color.RED);
        }
        listeners = new ArrayList<>();
    }

    public void setOnMyChangeListener(OnMyChangeListener listener) {
        listeners.add(listener);
    }
}
