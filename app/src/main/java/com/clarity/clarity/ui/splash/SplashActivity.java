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
        Hawk.put(StorageKeys.Token, "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIzIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbW9iaWxlcGhvbmUiOiJkNGRlMjYxZC1jNDk4LTRmNDItYmYxMi1kOGQ1ZThiMjg0MTAiLCJuYmYiOjE1NjAzNTcxNjksImV4cCI6MTU2MDM2MDc2OSwiaWF0IjoxNTYwMzU3MTY5fQ.PjpRxKglmdpP9KitoZ0bwY6SRd5bF3bHktQnFjr9k_Bcw1VaqZFx-eFKrDIGy9adQG6yZsLr6xOYw2F8sf0kkrkiUvcXs4BEq5LmMXEi18mlvDngjqp3M0qJbOqtBj3h7ehGTA_khgqadFqKydnAaQwgRAS3bu1v05gp4afmfS7qdPlBC0bZ__zrMY9nuZdIg1C9Wbbl1kL0UMIZkPlu59LiZkudDLgnST308AtoV3_HxeEVYqzrug3kIsYdLCxBZGnV72y_RPg_RcQwP5-JIJYibqTRyuTwqXYhQeykvhqu6LdcuwVN3VPA_-xRw6m4bRfh7cazR-8oV3ZLfcP7hQ");
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