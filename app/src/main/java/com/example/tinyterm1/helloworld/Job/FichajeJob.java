package com.example.tinyterm1.helloworld.Job;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.tinyterm1.helloworld.Activities.PrincipalActivity;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationSuccessInterface;
import com.example.tinyterm1.helloworld.Services.FichadoPost;
import com.example.tinyterm1.helloworld.Services.GeoLocationService;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostLastSuccess;

/**
 * Created by Pablo on 18/02/2018.
 */

public class FichajeJob extends Job {

    public static final String TAG = "job_fichaje_tag";
    private static TextView _txtViewUltimaFichada;
    private static TextView _txtViewProximaFichada;
    private static Context context;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        FichadoPost fichadoPost = new FichadoPost();

        fichadoPost.Post(context, new GeoLocationSuccessInterface() {
            @Override
            public void MostrarMensaje(String mensaje, String hora) {
                _txtViewUltimaFichada.setText(hora);
                GeoLocationPostLastSuccess.setLastSuccess(context, hora);
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                scheduleJob(hora);
            }
        }, new GeoLocationErrorInterface() {
            @Override
            public void MostrarMensaje(String mensaje, String hora) {
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                scheduleJob(hora);
            }
        });
        return Result.SUCCESS;
    }

    private void scheduleJob(String hora){
        int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(context));
        long moment = GeoLocationService.GetStartingMoment(intervalo);
        String proximaFichada = GeoLocationService.GetProximaFichada(hora, intervalo);
        _txtViewProximaFichada.setText(proximaFichada);
        scheduleJob(_txtViewUltimaFichada, _txtViewProximaFichada, context, moment);
    }

    public static void scheduleJob(TextView txtViewUltimaFichada, TextView txtViewProximaFichada, Context ctx, long ms) {
        _txtViewUltimaFichada = txtViewUltimaFichada;
        _txtViewProximaFichada = txtViewProximaFichada;
        context = ctx;
        new JobRequest.Builder(FichajeJob.TAG)
                .setExact(ms)
                .build()
                .schedule();
    }
}