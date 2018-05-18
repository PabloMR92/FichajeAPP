package com.clarity.clarity.ui.splash;

import android.os.Debug;
import android.util.Log;

import com.clarity.clarity.model.UserLogin;
import com.clarity.clarity.network.NetworkManager;
import com.clarity.clarity.rx.SchedulerProvider;
import com.clarity.clarity.ui.base.BasePresenter;
import com.clarity.clarity.utils.StorageKeys;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.Storage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    @Inject
    public SplashPresenter(SplashContract.View view,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable,
                           NetworkManager networkManager) {
        super(view, schedulerProvider, compositeDisposable, networkManager);
    }

    @Override
    public void waitSplash() {
        Log.d("waitSplash", "waitSplash");
        if (Hawk.contains(StorageKeys.Token)) {
            getCompositeDisposable()
                    .add(getmNetworkManager().validateToken()
                            .observeOn(getSchedulerProvider().ui())
                            .subscribeOn(getSchedulerProvider().io())
                            .subscribe(tokenInfo -> {
                                if (tokenInfo.getIsValid()) {
                                    Timber.d("SPLASH: Token registrado en la aplicación.");
                                    getView().goToMain();
                                } else {
                                    Timber.d("SPLASH-VALIDATION: Token inexistente.");
                                    Hawk.delete(StorageKeys.Token);
                                    getView().goToLogin();
                                }
                            }, e -> {
                                Timber.e("SPLASH-ERROR:" + e.getMessage());
                            }));
        } else {
            getView().goToLogin();
        }
    }
}
