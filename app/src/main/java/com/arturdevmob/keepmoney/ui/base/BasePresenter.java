package com.arturdevmob.keepmoney.ui.base;

import com.arturdevmob.keepmoney.data.DataManager;

public class BasePresenter<V extends BaseMvpView> implements BaseMvpPresenter<V> {
    private DataManager mDataManager;
    private V mView;

    public BasePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public V getView() {
        return mView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }
}
