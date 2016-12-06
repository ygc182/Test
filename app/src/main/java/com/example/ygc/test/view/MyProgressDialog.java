package com.example.ygc.test.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.ygc.test.R;

/**
 * Created by YGC on 16/10/13.
 */

public class MyProgressDialog extends ProgressDialog{
    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_item);
    }
}
