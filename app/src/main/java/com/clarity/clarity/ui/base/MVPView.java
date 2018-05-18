package com.clarity.clarity.ui.base;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

public interface MVPView {

    void showLoader();

    void hideLoader();

    void setUnbinder(Unbinder unbinder);
}
