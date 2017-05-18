package com.example.owenh.alarmo.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by owen on 2017/5/17
 */

public class BaseApplication extends Application {
    public static Context sAppContext;

    //需要再Manifest中注明name属性，否则不起作用
    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sAppContext;
    }
}
