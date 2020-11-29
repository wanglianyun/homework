package com.foglotus.demo.toutiao.util;

import android.widget.Toast;

import com.foglotus.demo.toutiao.TouTiao;

public class ToastUtil {
    public static void showToast(String msg){
        TouTiao.getHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TouTiao.getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
