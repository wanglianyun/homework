package com.foglotus.demo.toutiao.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy年M月d日").create();
    public static <T> T toObject(String json, Class<T> objectClass){
        return gson.fromJson(json,objectClass);
    }
    public static String toJson(Object data){
        return gson.toJson(data);
    }
    public static Gson getGson() {
        return gson;
    }
}
