package com.foglotus.demo.toutiao;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TouTiao.initialize(this);
    }
}
