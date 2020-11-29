package com.foglotus.demo.toutiao;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class TouTiao {

    private static Context context;  // 全局上下文

    private static Handler handler;  // 子线程更新UI


    public static void initialize(Context c) {
        context = c;
        handler = new Handler(Looper.getMainLooper());
    }

    public static Context getContext() {
        return context;
    }


    public static Handler getHandler() {
        return handler;
    }

}
