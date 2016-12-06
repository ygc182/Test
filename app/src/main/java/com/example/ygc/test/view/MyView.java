package com.example.ygc.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ygc.test.R;

public class MyView extends LinearLayout {
    private static final String TAG = MyView.class.getSimpleName();

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        for(int i=0;i<attrs.getAttributeCount();i++){
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
//            Log.d(TAG, "name: "+attributeName+" ,"+"value: "+attributeValue);
        }

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.MyView);
        String text = array.getString(R.styleable.MyView_text);
        String color = array.getString(R.styleable.MyView_color);
//        Log.d(TAG, "test: "+text+" , color: "+color);
        array.recycle();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
//        return super.onTouchEvent(event);
        return true;
    }
}
