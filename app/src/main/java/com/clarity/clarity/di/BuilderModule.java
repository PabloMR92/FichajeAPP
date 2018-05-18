package com.clarity.clarity.di;

import com.clarity.clarity.ui.login.LoginActivity;
import com.clarity.clarity.ui.main.MainActivity;
import com.clarity.clarity.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector(modules = {PresentersModule.class, ViewsModule.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {PresentersModule.class, ViewsModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {PresentersModule.class, ViewsModule.class})
    abstract LoginActivity bindLoginActivity();

}
