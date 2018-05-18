package com.clarity.clarity.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.clarity.clarity.R;
import com.clarity.clarity.model.UserLogin;
import com.clarity.clarity.ui.base.BaseActivity;
import com.clarity.clarity.ui.main.MainActivity;
import com.clarity.clarity.ui.main.MainContract;
import com.clarity.clarity.ui.splash.SplashContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginContract.Presenter mPresenter;

    @BindView(R.id.password)
    TextView txt_password;
    @BindView(R.id.user)
    TextView txt_user;
    @BindView(R.id.dni)
    TextView txt_dni;
    @BindView(R.id.btnLogin)
    TextView btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        mPresenter.attach(this);
        btn_login.setOnClickListener((View v) -> logIn());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    private void logIn() {
        mPresenter.logIn(new UserLogin(txt_user.getText().toString(), txt_password.getText().toString(), txt_dni.getText().toString()));
    }

    @Override
    public void goToMain() {
        Intent iMain = new Intent(this, MainActivity.class);
        iMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(iMain);
    }

    @Override
    public void showErrorMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
