package com.clarity.clarity;


import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.clarity.clarity.di.DaggerAppComponent;
import com.clarity.clarity.job.ClockInJobCreator;
import com.clarity.clarity.logger.FileLoggingTree;
import com.crashlytics.android.Crashlytics;
import com.evernote.android.job.JobManager;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

        Fabric.with(this, new Crashlytics());
        JodaTimeAndroid.init(this);
        Hawk.init(this).build();
        JobManager.create(this).addJobCreator(new ClockInJobCreator());
        Timber.plant(new FileLoggingTree(getApplicationContext()));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }
}