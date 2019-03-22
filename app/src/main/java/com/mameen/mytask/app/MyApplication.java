package com.mameen.mytask.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class MyApplication extends Application {

    public static Context context1;

    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication mInstance;

    public void onCreate() {
        super.onCreate();

        MyApplication.context1 = getApplicationContext();
        mInstance = this;
    }

    public static Context getAppContext() {
        return MyApplication.context1;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
