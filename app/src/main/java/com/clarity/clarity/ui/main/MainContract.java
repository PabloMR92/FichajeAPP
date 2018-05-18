package com.clarity.clarity.ui.main;


import android.location.Location;

import com.clarity.clarity.ui.base.MVPPresenter;
import com.clarity.clarity.ui.base.MVPView;

import org.joda.time.DateTime;

import io.reactivex.Observable;


public interface MainContract {

    interface View extends MVPView {
        void setLastSuccesfulClockIn(DateTime time);
        void showErrorMessage(String message);
        Observable<Location> getLocationObs();
    }

    interface Presenter extends MVPPresenter<View> {
        void clockIn();
        void scheduleClockInJob();
    }
}