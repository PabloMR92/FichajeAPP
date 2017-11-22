package com.example.tinyterm1.helloworld.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class DireccionSV {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "DireccionSV";

    public DireccionSV() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getDireccion(Context context) {
        String dir = getPrefs(context).getString("direccion", null);
        if(dir == null)
            dir = "http://190.210.39.161/WebApiFichaje/";
        return dir;
    }

    public static void setDireccion(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("direccion", input);
        editor.commit();
    }
}
