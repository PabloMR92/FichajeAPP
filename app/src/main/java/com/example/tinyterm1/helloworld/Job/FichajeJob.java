package com.example.tinyterm1.helloworld.Job;

import android.app.Activity;
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
    private static PrincipalActivity context;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        FichadoPost fichadoPost = new FichadoPost();

        fichadoPost.Post(context, new GeoLocationSuccessInterface() {
            @Override
            public void MostrarMensaje(String mensaje, String hora) {
                try{
                    SetUltimaFichada(hora);
                    GeoLocationPostLastSuccess.setLastSuccess(context, hora);
                    Toast(mensaje);
                }
                catch(Exception e){
                    throw new IllegalArgumentException(e);
                }
            }
        }, new GeoLocationErrorInterface() {
            @Override
            public void MostrarMensaje(String mensaje, String hora) {
                try{
                    Toast(mensaje);
                }
                catch(Exception e){
                    throw new IllegalArgumentException(e);
                }
            }
        });
        listId.remove(Integer.valueOf(params.getId()));
        try{
            scheduleJob();
        }
        catch(Exception e){
            throw new IllegalArgumentException(e);
        }
        return Result.SUCCESS;
    }

    private void scheduleJob(){
        int intervalo = Integer.parseInt(GeoLocationPostInterval.getInterval(context));
        long moment = GeoLocationService.GetStartingMoment(intervalo);
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String proximaFichada = GeoLocationService.GetProximaFichada(formatoFecha.format(cal.getTime()), intervalo);
        SetProximaFichada(proximaFichada);
        scheduleJob(context, moment, false);
    }

    public static void scheduleJob(PrincipalActivity ctx, long ms, boolean updateCurrent) {
        context = ctx;
        Integer id =new JobRequest.Builder(FichajeJob.TAG)
                .setExact(ms)
                .setUpdateCurrent(updateCurrent)
                .build()
                .schedule();
        listId.add(id);
    }

    private void Toast(final String txt){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.Toast(txt);
            }
        });
    }

    private void SetUltimaFichada(final String txt){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.setIltimaFichadaExitosa(txt);
            }
        });
    }

    private void SetProximaFichada(final String txt){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.setProximaFichada(txt);
            }
        });
    }
}