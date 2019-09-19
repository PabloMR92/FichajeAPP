package com.clarity.clarity.ui.main;

import com.clarity.clarity.BuildConfig;
import com.clarity.clarity.job.ClockInJob;
import com.clarity.clarity.model.Configuration;
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
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.cert.PKIXRevocationChecker;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
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
                                .retryWhen(attempt -> attempt
                                        .zipWith(Observable.range(1, 4), (error, repeatAttempt) ->
                                                new RetryObj(error, repeatAttempt))
                                        .flatMap(obj -> {
                                            if (obj.getRetries() <= 3 && obj.getError() instanceof SocketTimeoutException)
                                                return Observable.timer(obj.getRetries() * 2, TimeUnit.SECONDS);
                                            return Observable.error(obj.getError());
                                        }))
                                .observeOn(getSchedulerProvider().ui())
                                .subscribeOn(getSchedulerProvider().io()))
                        .subscribe((result) -> {
                            if (result.code() == 200) {
                                Timber.d("FICHADO: Fichado exitoso.");
                                getView().setLastSuccesfulClockIn(requestTime);
                                Hawk.put(StorageKeys.LastSuccesfulClockIn, DateTimeFormat.forPattern("HH:mm:ss dd-MM-yyyy").print(requestTime));
                                getView().showErrorMessage("Fichado exitoso.");
                            }
                            else if(result.code() == 400){
                                ResponseBody responseBody = result.errorBody();
                                Timber.d("FICHADO-VALIDACION: No se encontró establecimiento cercano.");
                                getView().showErrorMessage(getmNetworkManager().getErrorMessage(responseBody));
                            }
                        }, e -> {
                            if (e instanceof HttpException) {
                                ResponseBody responseBody = ((HttpException) e).response().errorBody();
                                if (((HttpException) e).code() == 400) {
                                    Timber.d("FICHADO-VALIDACION: No se encontró establecimiento cercano.");
                                    getView().showErrorMessage(getmNetworkManager().getErrorMessage(responseBody));
                                } else {
                                    getView().showErrorMessage("Ocurrio un error al intentar realizar la operacion. Comuniquese con un administrador.");
                                }
                            } else if (e instanceof TimeoutException) {
                                Timber.d("FICHADO-ERROR: No se pudo obtener ubicación. " + requestTime.toString());
                                getView().showErrorMessage("No se pudo comunicar con el servidor. Intentelo de nuevo mas tarde.");
                            } else {
                                Timber.d("FICHADO-ERROR: No se pudo comunicar con el servidor.");
                                getView().showErrorMessage("No se pudo comunicar con el servidor. Intentelo de nuevo mas tarde.");
                            }
                        }));
    }

    @Override
    public void scheduleClockInJob() {
        getCompositeDisposable()
                .add(getmNetworkManager().getConfiguration()
                        .retryWhen(attempt -> attempt
                                .zipWith(Observable.range(1, 5), (error, repeatAttempt) ->
                                        new RetryObj(error, repeatAttempt))
                                .flatMap(obj -> {
                                    if (obj.getRetries() <= 4 && obj.getError() instanceof SocketTimeoutException)
                                        return Observable.timer(obj.getRetries() * 2, TimeUnit.SECONDS);
                                    return Observable.error(obj.getError());
                                }))
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(configuration -> {
                            Hawk.put(StorageKeys.Configuration,configuration);
                            scheduleClockInJob(configuration);
                        }, e -> {
                            Configuration config = Hawk.get(StorageKeys.Configuration);
                            if(config != null) {
                                scheduleClockInJob(config);
                            }
                            else
                                scheduleClockInJob(new Configuration(BuildConfig.AUTOMATIC_CLOCK_IN_INTERVAL));
                        }));
    }

    private void scheduleClockInJob(Configuration configuration){
        if (configuration.getClockInInterval() != 0) {
            ClockInJob.scheduleJob(this, configuration.getClockInInterval());
        }
    }
}