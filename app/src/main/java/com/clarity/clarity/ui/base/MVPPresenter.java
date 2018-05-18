package com.clarity.clarity.ui.base;

public interface MVPPresenter<T extends MVPView> {

    void attach(T view);

    void detach();

}