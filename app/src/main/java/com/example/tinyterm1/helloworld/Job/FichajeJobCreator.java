package com.example.tinyterm1.helloworld.Job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by Pablo on 18/02/2018.
 */

public class FichajeJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case FichajeJob.TAG:
                return new FichajeJob();
            default:
                return null;
        }
    }
}
