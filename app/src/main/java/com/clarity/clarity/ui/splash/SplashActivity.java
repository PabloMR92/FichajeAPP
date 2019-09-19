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
        Hawk.put(StorageKeys.Token, "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI4MzYiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9tb2JpbGVwaG9uZSI6IjczY2QzYzQxLTdkZjUtNDc4Yy04YzgyLTY5OGI4M2Y3MmNiYiIsIm5iZiI6MTU2ODkxNDU3OCwiZXhwIjoxNTY4OTE4MTc4LCJpYXQiOjE1Njg5MTQ1Nzh9.K4JDawdciPxl8LTT58V240zJqbM0-Vg_YHXY3FwhO8ryazcH0YQBcvwbcidf36phU3bCHy7SX0yp3kUeiwxWY_T7yndwuVuXO44Po2soI-0shegufpW9RzMoH774tvHt3ciINjToSjqu9xTcjkSTxIU-ZPUIfxO01QeRBK277V2PgWUdUFpt6BOz3bifM-z944vfkNS_P7I0yO_obab1U53uvGlj_uitwxWJFCBZ0qxd5VSYMlMBZu2i8d9t0jzVeJCe6yMLI9LCGalc9WfKGsvs4rHAjbjhiM7PyjHrfEWoJEuXGFBkQWces017on3fEDkutqjYIJSh7WyxZs8PXQ");
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