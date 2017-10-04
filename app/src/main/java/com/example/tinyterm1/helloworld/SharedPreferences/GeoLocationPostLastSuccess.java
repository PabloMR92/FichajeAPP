package com.example.tinyterm1.helloworld.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class GeoLocationPostLastSuccess {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "GeoLocationPostLastSuccess";

    public GeoLocationPostLastSuccess() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getLastSuccess(Context context) {
        return getPrefs(context).getString("lastSuccess", null);
    }

    public static void setLastSuccess(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("lastSuccess", input);
        editor.commit();
    }
}
