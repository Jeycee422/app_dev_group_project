package com.example.grocery_list_app_dev;

import android.content.Context;
import android.content.SharedPreferences;

import room.User;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user) {

        int id = user.getUser_id();
        editor.putInt(SESSION_KEY,id).commit();

    }
    public int getSession() {
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }
    public void removeSession() {
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
