package com.clarity.clarity.ui.login;

import com.clarity.clarity.model.JWT;
import com.clarity.clarity.model.UserLogin;
import com.clarity.clarity.network.NetworkManager;
import com.clarity.clarity.rx.SchedulerProvider;
import com.clarity.clarity.ui.base.BasePresenter;
import com.clarity.clarity.utils.StorageKeys;
import com.orhanobut.hawk.Hawk;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter(LoginContract.View view,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable,
                          NetworkManager networkManager) {
        super(view, schedulerProvider, compositeDisposable, networkManager);
    }

    public void logIn(UserLogin credentials) {
        getCompositeDisposable()
                .add(getmNetworkManager().logIn(credentials)
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe(token -> {
                            Timber.d("LOGIN: Exitoso.");
                            Hawk.put(StorageKeys.Token, token.getToken());
                            getView().goToMain();
                        }, e -> {
                            if (e instanceof HttpException) {
                                ResponseBody responseBody = ((HttpException) e).response().errorBody();
                                if (((HttpException) e).code() == 400) {
                                    getView().showErrorMessage(getmNetworkManager().getErrorMessage(responseBody));
                                    Timber.d("LOGIN-VALIDATION: Error de credenciales.");
                                } else {
                                    getView().showErrorMessage("Ocurrió un error en el servidor. Comuníquese con un administrador");
                                    Timber.d("LOGIN-ERROR: Error interno del servidor (500).");
                                }
                            } else {
                                getView().showErrorMessage("LOGIN-ERROR: Problemas de conexión.");
                            }
                        }));
    }

    private void SaveUserToken(JWT token) {
        Timber.d("LOGIN: Exitoso.");
        Hawk.put(StorageKeys.Token, token.getToken());
    }
}
