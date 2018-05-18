package com.clarity.clarity.di;

import com.clarity.clarity.network.NetworkManager;
import com.clarity.clarity.rx.SchedulerProvider;
import com.clarity.clarity.ui.login.LoginContract;
import com.clarity.clarity.ui.login.LoginPresenter;
import com.clarity.clarity.ui.main.MainContract;
import com.clarity.clarity.ui.main.MainPresenter;
import com.clarity.clarity.ui.splash.SplashContract;
import com.clarity.clarity.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class PresentersModule {

    @Provides
    SplashContract.Presenter providesSplashPresenter(SplashContract.View view,
                                                     SchedulerProvider schedulerProvider,
                                                     CompositeDisposable compositeDisposable,
                                                     NetworkManager networkManager) {
        return new SplashPresenter(view, schedulerProvider, compositeDisposable, networkManager);
    }

    @Provides
    MainContract.Presenter providesMainPresenter(MainContract.View view,
                                                 SchedulerProvider schedulerProvider,
                                                 CompositeDisposable compositeDisposable,
                                                 NetworkManager networkManager) {
        return new MainPresenter(view, schedulerProvider, compositeDisposable, networkManager);
    }

    @Provides
    LoginContract.Presenter providesLoginPresenter(LoginContract.View view,
                                                 SchedulerProvider schedulerProvider,
                                                 CompositeDisposable compositeDisposable,
                                                 NetworkManager networkManager) {
        return new LoginPresenter(view, schedulerProvider, compositeDisposable, networkManager);
    }
}