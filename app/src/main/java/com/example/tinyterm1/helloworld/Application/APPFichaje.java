package com.example.tinyterm1.helloworld.Application;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.example.tinyterm1.helloworld.Job.FichajeJobCreator;

/**
 * Created by Pablo on 10/04/2018.
 */

public class APPFichaje extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(getApplicationContext()).addJobCreator(new FichajeJobCreator());
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}
