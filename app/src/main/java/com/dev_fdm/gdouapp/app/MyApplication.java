package com.dev_fdm.gdouapp.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * 全局Context
 * Created by Bruce on 2015/11/21.
 */
public class MyApplication extends Application {
    private static Context context;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
