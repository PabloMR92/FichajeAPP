package com.example.tinyterm1.helloworld.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TinyTerm1 on 02/01/2017.
 */

public class UUIDKeyValueDB {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "UUID";

    public UUIDKeyValueDB() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getUUID(Context context) {
        return "";//getPrefs(context).getString("value", null);
    }

    public static void setUUID(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("value", input);
        editor.commit();
    }
}


