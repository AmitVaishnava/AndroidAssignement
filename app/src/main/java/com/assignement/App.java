package com.assignement;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static Context mAppContext;

    public static void setAppInstance(Context context) {
        if (mAppContext == null) {
            mAppContext = context;
        }
    }

    public static Context getInstance() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAppInstance(this);
    }
}
