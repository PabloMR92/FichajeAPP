package com.example.tinyterm1.helloworld.Services;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tinyterm1.helloworld.Activities.PrincipalActivity;
import com.example.tinyterm1.helloworld.Activities.ReporteActivity;
import com.example.tinyterm1.helloworld.Receiver.GeoLocationAlarmReceiver;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by TinyTerm1 on 03/01/2017.
 */

public class GeoLocationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(PendingIntent.getBroadcast(this,0,new Intent(this, GeoLocationAlarmReceiver.class),PendingIntent.FLAG_NO_CREATE));
    }
}

