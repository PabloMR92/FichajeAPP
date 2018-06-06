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
        //Hawk.put(StorageKeys.Token, "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI1NzIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9tb2JpbGVwaG9uZSI6IjJiNTI3YWI0LWQ3ZWEtNDM3ZC04OGQwLTk2NjA0NzU3MTRmMCIsIm5iZiI6MTUyODEyMzY0MiwiZXhwIjoxNTI4MTI3MjQyLCJpYXQiOjE1MjgxMjM2NDJ9.P2IV4-D1B14rGeQwxp9qqSLu1cug_JyUlkjmLHvMcwUBesNZIvlpw-FteCX7Jr2AtHml4VP6Cd6nCSGVHmd6ogj7kbyjsfzrg86NnPnbp0tqluDo3_0sF2iE8f4DL6shEh7z3I6bnm3mDpKkEXsXUogQRFskx0GpIywtZxqhigS6X15x8adqp8ROOiVx_eqRXX3HrC9Wl4ExWUc0KLfxhCDUDO5dSsyLQd0g68b2iucTR34OziW35JkCU4ai9QxXiXmSZPzfb7WuBmruviGmcVWhVESgI1XrRbWIwsYU1d0_usRUTuXvhjarwo7IhPGxn3mzH6h79D6GFhNbRGOjBw");
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