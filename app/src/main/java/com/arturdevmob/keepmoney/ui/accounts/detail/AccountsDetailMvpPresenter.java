package com.arturdevmob.keepmoney.ui.accounts.detail;

import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpPresenter;

public interface AccountsDetailMvpPresenter<V extends AccountsDetailMvpView> extends BaseMvpPresenter<V> {
    void loadAccountTransactions(long accountId);
    void deleteTransaction(TransactionModels transactionModels);
    void addTransaction(long accountId);
    void editTransaction(TransactionModels transactionModels);
    void loadAccountBalanceToolbar(long accountId);
}
