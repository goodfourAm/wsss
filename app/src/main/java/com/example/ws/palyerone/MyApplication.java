package com.example.ws.palyerone;

import android.app.Application;
import android.content.Context;

/**
 * Created by ws on 2018/4/23.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
