package com.clarity.clarity.ui.splash;

import com.clarity.clarity.ui.base.MVPPresenter;
import com.clarity.clarity.ui.base.MVPView;

public interface SplashContract {

    interface View extends MVPView {
        void goToLogin();
        void goToMain();
        void closeApp();
    }

    interface Presenter extends MVPPresenter<View> {
        void waitSplash();
    }
}