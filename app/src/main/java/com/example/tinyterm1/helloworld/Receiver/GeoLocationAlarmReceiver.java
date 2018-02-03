package com.example.tinyterm1.helloworld.Receiver;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.Activities.PrincipalActivity;
import com.example.tinyterm1.helloworld.Adapter.ReporteListAdapter;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationApiCall;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationSuccessInterface;
import com.example.tinyterm1.helloworld.ApiCall.ReporteApiCall;
import com.example.tinyterm1.helloworld.ApiCall.ReporteSuccessInterface;
import com.example.tinyterm1.helloworld.Models.GeoLocation;
import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.Models.Reporte;
import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.Services.FichadoPost;
import com.example.tinyterm1.helloworld.Services.GPSTracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.ALARM_SERVICE;

/**
 * Created by TinyTerm1 on 03/01/2017.
 */

public class GeoLocationAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        FichadoPost fichadoPost = new FichadoPost();
        fichadoPost.Post(context, new GeoLocationSuccessInterface() {
            @Override
            public void MostrarMensaje(String mensaje, String hora) throws ParseException {
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                Intent updateUIIntent = new Intent("UPDATE_FECHA");
                updateUIIntent.putExtra("hora",hora);
                context.sendBroadcast(updateUIIntent);
            }
        }, new GeoLocationErrorInterface() {
            @Override
            public void MostrarMensaje(String mensaje, String hora) {
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                Intent updateUIIntent = new Intent("UPDATE_FECHA_ERROR");
                updateUIIntent.putExtra("hora",hora);
                context.sendBroadcast(updateUIIntent);
            }
        });
    }
}
