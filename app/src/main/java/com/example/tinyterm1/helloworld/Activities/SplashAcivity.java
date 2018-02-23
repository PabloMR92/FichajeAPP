package com.example.tinyterm1.helloworld.Activities;

import com.crashlytics.android.Crashlytics;
import com.evernote.android.job.JobManager;
import com.example.tinyterm1.helloworld.ApiCall.LoginApiCall;
import com.example.tinyterm1.helloworld.ApiCall.LoginErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.LoginSuccessInterface;
import com.example.tinyterm1.helloworld.ApiCall.UUIDApiCall;
import com.example.tinyterm1.helloworld.ApiCall.UUIDSuccessInterface;
import com.example.tinyterm1.helloworld.Job.FichajeJobCreator;
import com.example.tinyterm1.helloworld.Models.LoginErrorRequest;
import com.example.tinyterm1.helloworld.Models.UserLogin;
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
import android.widget.Toast;

import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashAcivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCenter.start(getApplication(), "9ee99dd5-1ab0-4469-b41d-1251c0d94de6",
                Analytics.class, Crashes.class);
        super.onCreate(savedInstanceState);
        JobManager.create(getApplicationContext()).addJobCreator(new FichajeJobCreator());
        setContentView(R.layout.activity_splash_acivity);
        if (UUIDKeyValueDB.getUUID(this) != null) {
            UUIDApiCall uuidApi = new UUIDApiCall();
            Map<String, String> data = new HashMap<>();
            data.put("UUID", UUIDKeyValueDB.getUUID(this));
            uuidApi.getUUID(data, this,
                    new UUIDSuccessInterface() {
                        @Override
                        public void Existe(boolean existe) {
                            if(existe){
                                SetStartPrincipalActivity();
                            }
                            else{
                                SetStartLoginActivity();
                            }
                        }
                    }
            );
        }
        else
        {
            SetStartLoginActivity();
        }
        Fabric.with(this, new Crashlytics());
    }

    private void SetStartLoginActivity(){
        Intent inicioActivityIntent = new Intent(this, LoginActivity.class);
        inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(inicioActivityIntent);
    }

    private void SetStartPrincipalActivity(){
        Intent inicioActivityIntent = new Intent(this, PrincipalActivity.class);
        inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(inicioActivityIntent);
    }
}
