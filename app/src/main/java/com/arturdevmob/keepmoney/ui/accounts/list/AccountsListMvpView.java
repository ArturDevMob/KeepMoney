package com.arturdevmob.keepmoney.ui.accounts.list;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.ui.base.BaseMvpView;

import java.util.List;

public interface AccountsListMvpView extends BaseMvpView {
    void showAccounts(List<AccountModels> accounts);
    void showAccountsEmpty();
    void startAccountEditActivity(AccountModels accountModels);
    void showHideControlElements();
    void showAlertDialogForAccount(AccountModels accountModels);
    void updateAdapterAccounts();
    void deleteItemInAdapter(AccountModels accountModels);
    void startDetailFragment(AccountModels accountModels);
    void showAccountsBalanceToolbar(double balance, CurrencyType currencyType);
    void showAccountsBalanceErrorToolbar();
    void showDialogWithGeneralBalance();
    void showDialogWithErrorGeneralBalance();
}
