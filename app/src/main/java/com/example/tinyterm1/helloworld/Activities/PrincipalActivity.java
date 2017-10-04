package com.example.tinyterm1.helloworld.Activities;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.ApiCall.GeoLocationErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationSuccessInterface;
import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.Receiver.GeoLocationAlarmReceiver;
import com.example.tinyterm1.helloworld.Services.FichadoPost;
import com.example.tinyterm1.helloworld.Services.GeoLocationService;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostLastSuccess;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends AppCompatActivity {
    @BindView(R.id.txtproximaFichadaDesc)
    TextView proximaFichada;
    @BindView(R.id.txtultimaFichadaExitosaDesc)
    TextView ultimaFichadaExitosa;
    @BindView(R.id.btnfichadoManual)
    Button FichadoManualButton;
    PendingIntent pendingIntent;
    private Context ctx;
    private boolean esNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        ctx = this;
        esNuevo = true;
        FichadoManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FichadoPost fichadoPost = new FichadoPost();
                fichadoPost.Post(PrincipalActivity.this, new GeoLocationSuccessInterface() {
                    @Override
                    public void MostrarMensaje(String mensaje, String hora) {
                        ultimaFichadaExitosa.setText(hora);
                        GeoLocationPostLastSuccess.setLastSuccess(ctx, hora);
                        Toast.makeText(PrincipalActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                    }
                }, new GeoLocationErrorInterface() {
                    @Override
                    public void MostrarMensaje(String mensaje, String hora) {
                        Toast.makeText(PrincipalActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        startService();
        registerReceiver(broadcastReceiverFichadaExitosa, new IntentFilter("UPDATE_FECHA"));
        registerReceiver(broadcastReceiverFichadaFallida, new IntentFilter("UPDATE_FECHA_ERROR"));
        int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(this));
        Calendar calendar = GetStartingMoment(intervalo);
        Date date = calendar.getTime();
        proximaFichada.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        ultimaFichadaExitosa.setText(GeoLocationPostLastSuccess.getLastSuccess(ctx));
    }

    private String GetProximaFichada(String fecha){
        int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(ctx));
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


    BroadcastReceiver broadcastReceiverFichadaExitosa = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fecha = intent.getStringExtra("hora");
            ultimaFichadaExitosa.setText(fecha);
            proximaFichada.setText(GetProximaFichada(fecha));
            GeoLocationPostLastSuccess.setLastSuccess(ctx, fecha);
            startAlarm();
        }
    };

    BroadcastReceiver broadcastReceiverFichadaFallida = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fecha = intent.getStringExtra("hora");
            proximaFichada.setText(GetProximaFichada(fecha));
            startAlarm();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent inicioActivityIntent;
        switch(item.getItemId()) {
        case R.id.config:
            inicioActivityIntent = new Intent(this, ConfiguracionActivity.class);
            this.startActivity(inicioActivityIntent);
            return(true);
        case R.id.reporte:
            inicioActivityIntent = new Intent(this, ReporteActivity.class);
            this.startActivity(inicioActivityIntent);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    public void startService() {
        Intent alarmIntent = new Intent(ctx, GeoLocationAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(ctx, 0, alarmIntent, 0);
        startAlarm();
        startService(new Intent(getBaseContext(), GeoLocationService.class));
    }

    public void startAlarm() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT || (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) && esNuevo)) {
            int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(this));
            Calendar calendar = GetStartingMoment(intervalo);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                manager.setExact(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent);
            } else {
                manager.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        intervalo * 60 * 1000,
                        pendingIntent);
            }
            esNuevo = false;
        }
    }

    private Calendar GetStartingMoment(int intervalo){
        Calendar calendar = Calendar.getInstance();
        int minutoActual = calendar.get(Calendar.MINUTE);
        if (minutoActual % intervalo == 0)
            minutoActual += 1;
        int minutoAIniciar = (int) (Math.ceil((double) minutoActual / intervalo)) * intervalo;
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minutoAIniciar);
        return calendar;
    }

}
