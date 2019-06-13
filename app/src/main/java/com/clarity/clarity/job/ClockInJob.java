package com.clarity.clarity.job;

import android.support.annotation.NonNull;

import com.clarity.clarity.ui.main.MainPresenter;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ClockInJob extends Job {
    public static final String TAG = "ClockInJob_TAG";
    private static MainPresenter Presenter;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        Presenter.clockIn();
        return Result.SUCCESS;
    }

    public static int scheduleJob(MainPresenter presenter, int clockInInterval) {
        Presenter = presenter;
        return new JobRequest.Builder(ClockInJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(clockInInterval))
                .setRequirementsEnforced(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }
}
