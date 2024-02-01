package com.example.cursv;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    public static void setAuthSettings(String key, String value, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getAuthSettings(String key, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    public static void setIntIdHuman(String key, int value, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntIdHuman(String key,Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }
}
