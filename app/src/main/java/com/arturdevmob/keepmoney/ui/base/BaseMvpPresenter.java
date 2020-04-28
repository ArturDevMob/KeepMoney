package com.arturdevmob.keepmoney.ui.base;

public interface BaseMvpPresenter<V extends BaseMvpView> {
    void attachView(V view);
    void detachView();
    V getView();
}
