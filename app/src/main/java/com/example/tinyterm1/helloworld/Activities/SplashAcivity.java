package com.example.tinyterm1.helloworld.Activities;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class SplashAcivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCenter.start(getApplication(), "9ee99dd5-1ab0-4469-b41d-1251c0d94de6",
                Analytics.class, Crashes.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acivity);
        if (UUIDKeyValueDB.getUUID(this) != null) {
            Intent inicioActivityIntent = new Intent(this, PrincipalActivity.class);
            inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(inicioActivityIntent);
        }
        else
        {
            Intent inicioActivityIntent = new Intent(this, LoginActivity.class);
            inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(inicioActivityIntent);
        }
    }
}
