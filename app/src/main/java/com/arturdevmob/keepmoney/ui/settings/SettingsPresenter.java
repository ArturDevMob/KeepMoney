package com.arturdevmob.keepmoney.ui.settings;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import javax.inject.Inject;

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements SettingsMvpPresenter<V> {
    @Inject
    public SettingsPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
