package com.example.tinyterm1.helloworld.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class GeoLocationPostInterval {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "GeoLocationPostInterval";
    private static String defaultValue = "5";

    public GeoLocationPostInterval() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getInterval(Context context) {
        String value = getPrefs(context).getString("interval", null);
        return "1";//value == null ? defaultValue : value;
    }

    public static void setInterval(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("interval", input);
        editor.commit();
    }
}

