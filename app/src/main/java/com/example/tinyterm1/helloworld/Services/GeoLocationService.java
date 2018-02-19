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
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by TinyTerm1 on 03/01/2017.
 */

public class GeoLocationService {

    public static long GetStartingMoment(int intervalo){
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int minutoActual = calendar.get(Calendar.MINUTE);
        if (minutoActual % intervalo == 0)
            minutoActual += 1;
        int minutoAIniciar = (int) (Math.ceil((double) minutoActual / intervalo)) * intervalo;
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minutoAIniciar);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String test = formatoFecha.format(calendar.getTime());
        return calendar.getTimeInMillis() - calendar2.getTimeInMillis();
    }

    public static String GetProximaFichada(String fecha, int intervalo){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(formatoFecha.parse(fecha));
        }
        catch(ParseException e) {
        }
        cal.add(Calendar.MINUTE, intervalo);
        return formatoFecha.format(cal.getTime());
    }
}

