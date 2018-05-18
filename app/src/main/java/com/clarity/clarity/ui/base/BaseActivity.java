package com.clarity.clarity.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.clarity.clarity.R;
import com.clarity.clarity.utils.ProgressDialogUtils;

import butterknife.Unbinder;
import dagger.android.AndroidInjection;

public class BaseActivity extends AppCompatActivity implements MVPView {

    private ProgressDialog mLoader;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUnbinder(Unbinder unbinder) {
        mUnBinder = unbinder;
    }

    @Override
    public void showLoader() {
        this.hideLoader();
        this.mLoader = ProgressDialogUtils.showLoader(this);
    }

    @Override
    public void hideLoader() {
        if (mLoader != null) {
            mLoader.cancel();
        }
    }

}
