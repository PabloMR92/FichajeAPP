package com.clarity.clarity.ui.splash;

import android.content.Intent;
import android.os.Bundle;

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
        // Hawk.put(StorageKeys.Token, "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIzIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbW9iaWxlcGhvbmUiOiJhYjlkMDk2Mi1hN2UwLTQ4ZDctYmI0NC0xY2MxM2EwNTZmYTkiLCJuYmYiOjE1MjQ3NDcxNDMsImV4cCI6MTUyNDc1MDc0MywiaWF0IjoxNTI0NzQ3MTQzfQ.SSyQzRFd5hL0wtKQhQaaeqbHGDJT1inUABYE6csOJP5Q6TQAkDcSJtQqEkZ4jnmGeMHJQGrSTmXHDwLiIAL4au6iO5mgUfoFtXbtjhAqH4slYqGvEmfO2JF6bd_3I1jYVIp4tD1_3xI-COuqP3iZukBYI-AqBWrBAglCJ7cDMHa4KlfieiHlQjsU_dXsNBdd0zkpasGgQ2Hn_16mskAqqctw0uXJ9DUa-Zjj_G6ex0sHVFJd0B6HH2N9Y-4P4iSFM_5KSYmXWh7831LkOgJfSaPcv6PdXzFTHTFaz7tp5TAgN3DgXHMhIAAouWvenIqcD9BQrBD4eVw5OE0qB2zXWg");
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}