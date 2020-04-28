package com.arturdevmob.keepmoney.ui.accounts.list;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpPresenter;

public interface AccountsListMvpPresenter<V extends AccountsListMvpView> extends BaseMvpPresenter<V> {
    void loadAccounts();
    void addAccount();
    void editAccount(AccountModels accountModels);
    void editListAccounts();
    void onClickAccount(AccountModels accountModels);
    void longClickAccount(AccountModels accountModels);
    void deleteAccounts(AccountModels accountModels);
    void loadAccountsBalanceToolbar();
    void onClickGeneralBalanceToolbar();
    void onClickGeneralBalanceErrorToolbar();
}
