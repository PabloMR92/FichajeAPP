package com.clarity.clarity.ui.login;

import com.clarity.clarity.model.UserLogin;
import com.clarity.clarity.ui.base.MVPPresenter;
import com.clarity.clarity.ui.base.MVPView;
import com.clarity.clarity.ui.main.MainContract;


public interface LoginContract {

    interface View extends MVPView {
        void goToMain();
        void showErrorMessage(String message);
    }

    interface Presenter extends MVPPresenter<LoginContract.View> {
        void logIn(UserLogin credentials);
    }
}