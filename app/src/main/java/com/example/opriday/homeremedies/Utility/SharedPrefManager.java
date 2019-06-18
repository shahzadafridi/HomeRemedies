package com.example.opriday.homeremedies.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Opriday on 11/30/2018.
 */

public class SharedPrefManager {

    //Request


    public SharedPrefManager() {

    }

    public static SharedPreferences.Editor getCustomSharedPreferencesEditor(Context context, String name,int mode) {
        SharedPreferences preferences = context.getSharedPreferences(name,mode);
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }
    public static SharedPreferences getCustomSharedPreferences(Context context, String name,int mode){
        SharedPreferences preferences = context.getSharedPreferences(name, mode);
        return preferences;
    }


}
