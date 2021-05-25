package com.boog24;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences("PREFS", PRIVATE_MODE);
        editor = pref.edit();
    }
    // clear all shared preference data after logout
    public void clearSession() {
        editor.clear();
        editor.commit();

    }


    public String getData() {
        String flag = pref.getString("data", "");
        editor.commit();
        return flag;
    }

    public void setData(String data) {
        editor.putString("data", data);
        editor.commit();
    }


}

