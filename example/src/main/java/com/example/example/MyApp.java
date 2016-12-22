package com.example.example;

import android.app.Application;
import android.content.Context;

import com.occall.nuts.NutsManager;

public class MyApp extends Application{
    public static Context sContext;

    @Override
    public void onCreate() {
        sContext = getApplicationContext();
        NutsManager.init(this,"58588ddb86ca040001b0d334");
    }
}
