package com.arturdevmob.keepmoney.ui.about;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import javax.inject.Inject;

public class AboutPresenter<V extends AboutMvpView> extends BasePresenter<V> implements AboutMvpPresenter<V> {
    @Inject
    public AboutPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
