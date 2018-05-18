package com.clarity.clarity.di;

import com.clarity.clarity.ui.login.LoginActivity;
import com.clarity.clarity.ui.login.LoginContract;
import com.clarity.clarity.ui.main.MainActivity;
import com.clarity.clarity.ui.main.MainContract;
import com.clarity.clarity.ui.splash.SplashActivity;
import com.clarity.clarity.ui.splash.SplashContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewsModule {
    @Binds
    abstract SplashContract.View providesSplashActivity(SplashActivity splashActivity);

    @Binds
    abstract MainContract.View providesMainActivity(MainActivity mainActivity);

    @Binds
    abstract LoginContract.View providesLoginActivity(LoginActivity mainActivity);
}