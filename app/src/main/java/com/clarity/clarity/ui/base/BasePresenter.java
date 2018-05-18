package com.clarity.clarity.ui.base;

import com.clarity.clarity.network.NetworkManager;
import com.clarity.clarity.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends MVPView> implements MVPPresenter<T> {

    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;
    private final NetworkManager mNetworkManager;
    private T mView;

    @Inject
    public BasePresenter(T view,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         NetworkManager networkManager) {
        this.mView = view;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
        this.mNetworkManager = networkManager;
    }

    public T getView() {
        return mView;
    }

    @Override
    public void attach(T view) {
        this.mView = view;
    }

    @Override
    public void detach() {
        mCompositeDisposable.clear();
        this.mView = null;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public NetworkManager getmNetworkManager() {
        return mNetworkManager;
    }
}
