package com.example.ygc.test.view;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    public static Context sContext ;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
