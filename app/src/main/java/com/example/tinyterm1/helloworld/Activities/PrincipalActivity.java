package com.example.tinyterm1.helloworld.Activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
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

import com.evernote.android.job.JobManager;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationSuccessInterface;
import com.example.tinyterm1.helloworld.Job.FichajeJob;
import com.example.tinyterm1.helloworld.Job.FichajeJobCreator;
import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.Receiver.GeoLocationAlarmReceiver;
import com.example.tinyterm1.helloworld.Services.FichadoPost;
import com.example.tinyterm1.helloworld.Services.GeoLocationService;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostLastSuccess;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static android.R.attr.data;

public class PrincipalActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    @BindView(R.id.txtproximaFichadaDesc)
    TextView proximaFichada;
    @BindView(R.id.txtultimaFichadaExitosaDesc)
    TextView ultimaFichadaExitosa;
    @BindView(R.id.btnfichadoManual)
    Button FichadoManualButton;
    PendingIntent pendingIntent;
    private Context ctx;
    private static final String TAG = "PrincipalActivity";
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        ctx = this;
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
        int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(this));
        FichajeJob.scheduleJob(ultimaFichadaExitosa, proximaFichada, PrincipalActivity.this, GeoLocationService.GetStartingMoment(intervalo));
        /*startService();
        registerReceiver(broadcastReceiverFichadaExitosa, new IntentFilter("UPDATE_FECHA"));
        registerReceiver(broadcastReceiverFichadaFallida, new IntentFilter("UPDATE_FECHA_ERROR"));*/

        Calendar calendar = GetStartingMoment(intervalo);
        Date date = calendar.getTime();
        proximaFichada.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        ultimaFichadaExitosa.setText(GeoLocationPostLastSuccess.getLastSuccess(ctx));
        if(!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION))
            EasyPermissions.requestPermissions(this, "La aplicación requiere el gps activado para funcionar correctamente.", 1159, Manifest.permission.ACCESS_FINE_LOCATION);
        final LocationManager manager = (LocationManager) PrincipalActivity.this.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(PrincipalActivity.this)) {
            enableLoc();
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(PrincipalActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //locationRequest.setInterval(30 * 1000);
            //locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(PrincipalActivity.this, REQUEST_LOCATION);

                                //finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
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
        int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(this));
        Calendar calendar = GetStartingMoment(intervalo);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            manager.setExact(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, PrincipalActivity.this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }
}
