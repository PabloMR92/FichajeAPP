package com.example.tinyterm1.helloworld.Job;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Pablo on 18/02/2018.
 */

public class FichajeJob extends Job {

    public static final String TAG = "job_fichaje_tag";
    private static List<Integer> listId = new ArrayList<Integer>();
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
                postToastMessage(mensaje);
                scheduleJob(hora);
            }
        }, new GeoLocationErrorInterface() {
            @Override
            public void MostrarMensaje(String mensaje, String hora) {
                postToastMessage(mensaje);
                scheduleJob(hora);
            }
        });
        listId.remove(Integer.valueOf(params.getId()));
        return Result.SUCCESS;
    }

    private void scheduleJob(String hora){
        int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(context));
        long moment = GeoLocationService.GetStartingMoment(intervalo);
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String proximaFichada = GeoLocationService.GetProximaFichada(formatoFecha.format(cal.getTime()), intervalo);
        _txtViewProximaFichada.setText(proximaFichada);
        scheduleJob(_txtViewUltimaFichada, _txtViewProximaFichada, context, moment, false);
    }

    public static void scheduleJob(TextView txtViewUltimaFichada, TextView txtViewProximaFichada, Context ctx, long ms, boolean updateCurrent) {
        _txtViewUltimaFichada = txtViewUltimaFichada;
        _txtViewProximaFichada = txtViewProximaFichada;
        context = ctx;
        Integer id =new JobRequest.Builder(FichajeJob.TAG)
                .setExact(ms)
                .setUpdateCurrent(updateCurrent)
                .build()
                .schedule();
        listId.add(id);
    }

    public void postToastMessage(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}