package com.example.tinyterm1.helloworld.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.SharedPreferences.DireccionSV;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfiguracionActivity extends AppCompatActivity {
    @BindView(R.id.direccionSvDesc)
    EditText DireccionSv;
    @BindView(R.id.intervaloDesc)
    EditText IntervaloDesc;
    @BindView(R.id.btnGuardarConfig)
    Button guardarConfigBtn;
    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        ButterKnife.bind(this);
        DireccionSv.setText(DireccionSV.getDireccion(this));
        IntervaloDesc.setText(GeoLocationPostInterval.getInterval(this));

        guardarConfigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarConfig();
                Toast.makeText(ConfiguracionActivity.this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                doRestart(ctx);
            }
        });
    }

    private void GuardarConfig(){
        DireccionSV.setDireccion(this, DireccionSv.getText().toString());
        GeoLocationPostInterval.setInterval(this, IntervaloDesc.getText().toString());
    }

    public static void doRestart(Context c) {
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e("Error","Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e("Error", "Was not able to restart application, PM null");
                }
            } else {
                Log.e("Error", "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e("Error", "Was not able to restart application");
        }
    }
}
