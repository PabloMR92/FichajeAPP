package com.clarity.clarity.ui.main;

import com.clarity.clarity.job.ClockInJob;
import com.clarity.clarity.model.Geolocation;
import com.clarity.clarity.model.UserLogin;
import com.clarity.clarity.network.NetworkManager;
import com.clarity.clarity.rx.RetryObj;
import com.clarity.clarity.rx.SchedulerProvider;
import com.clarity.clarity.ui.base.BasePresenter;
import com.clarity.clarity.utils.StorageKeys;
import com.orhanobut.hawk.Hawk;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainContract.View view,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         NetworkManager networkManager) {
        super(view, schedulerProvider, compositeDisposable, networkManager);
    }

    @Override
    public void clockIn() {
        DateTime requestTime = new DateTime();
        Timber.d("FICHADO-INICIO: *---------------*");
        getCompositeDisposable()
                .add(getView()
                        .getLocationObs()
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .take(1)
                        .timeout(15, TimeUnit.SECONDS)
                        .flatMap(location -> getmNetworkManager()
                                .clockIn(new Geolocation(location.getLatitude(), location.getLongitude(), requestTime.toString()))
                                .retry((attempts, error) -> attempts <= 3 && error instanceof SocketTimeoutException)
                                .observeOn(getSchedulerProvider().ui())
                                .subscribeOn(getSchedulerProvider().io()))
                        .subscribe((result) -> {
                            Timber.d("FICHADO: Fichado exitoso.");
                            getView().setLastSuccesfulClockIn(requestTime);
                            Hawk.put(StorageKeys.LastSuccesfulClockIn, requestTime);
                        }, e -> {
                            if (e instanceof HttpException) {
                                ResponseBody responseBody = ((HttpException) e).response().errorBody();
                                if (((HttpException) e).code() == 400) {
                                    Timber.d("FICHADO-VALIDACION: No se encontró establecimiento cercano.");
                                    getView().showErrorMessage(getmNetworkManager().getErrorMessage(responseBody));
                                }
                            } else if (e instanceof TimeoutException) {
                                DateTime test = new DateTime();
                                String test2 = Seconds.secondsBetween(requestTime, test).toString();
                                Timber.d("FICHADO-ERROR: No se pudo obtener ubicación. " + requestTime.toString());
                            } else {
                                Timber.d("FICHADO-ERROR: No se pudo comunicar con el servidor.");
                            }
                        }));
    }

    @Override
    public void scheduleClockInJob() {
        ClockInJob.scheduleJob(this);
    }


}