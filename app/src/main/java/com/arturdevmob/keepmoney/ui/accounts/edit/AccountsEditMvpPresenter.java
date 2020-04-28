package com.arturdevmob.keepmoney.ui.accounts.edit;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpPresenter;

public interface AccountsEditMvpPresenter<V extends AccountsListEditMvpView> extends BaseMvpPresenter<V> {
    void loadAccount(long id);
    void loadDefaultCurrencyType();
    void addAccount(AccountModels accountModels);
    void setCurrencyType(boolean isEditMode);
}
