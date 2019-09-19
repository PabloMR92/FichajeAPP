package com.clarity.clarity.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.clarity.clarity.R;
import com.clarity.clarity.ui.login.LoginActivity;
import com.clarity.clarity.ui.main.MainActivity;
import com.clarity.clarity.ui.base.BaseActivity;
import com.clarity.clarity.utils.StorageKeys;
import com.orhanobut.hawk.Hawk;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter.attach(this);
        //Hawk.put(StorageKeys.Token, "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI4MzYiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9tb2JpbGVwaG9uZSI6ImU4MzBmNWViLTA3NDItNDljMC1hYzUwLTFhNTEzMDE1ODljNyIsIm5iZiI6MTU2ODgyMDc1MSwiZXhwIjoxNTY4ODI0MzUxLCJpYXQiOjE1Njg4MjA3NTF9.W3Tf7oOrCVIB9JOh9yo0hTviWxmkn0AWW9AWNA80zic4Q0oHjM35DjqBgJkAAajtY0-XoRbdV-0d1ICQBy9Ri0foxCP7iaKHJ4V9ch9aF3Bqc86oCLNiIiU-E0DnbY0iwhDEA7xRAVsjs1x_AM2clJv8bRSEjFcgupWrw-qNiUxbQ2xrY5uh55BOQ1UYI3PU0iVlN0AkAl-D2uvW8bOPILHmSG8_qhuUNzmLxsVuPTP-fsH09s5lc9q5hTfneRBTWUYKKV6fJg7CZzPWbcyqg-OqBwdCYejlFTbVb5IQukbXJYl8zzzl7A3Z0QzXiJicg5ZREOQdlwYI9dclPnNRIQ");
        // Hawk.delete(StorageKeys.Token);
        mPresenter.waitSplash();
    }

    @Override
    public void goToMain() {
        Intent iMain = new Intent(this, MainActivity.class);
        iMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(iMain);
    }

    @Override
    public void goToLogin() {
        Intent iLogin = new Intent(this, LoginActivity.class);
        iLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(iLogin);
    }

    @Override
    public void closeApp() {
        Toast.makeText(this, "No se pudo conectar con el servidor. Inténtelo denuevo más tarde.", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}