package com.arturdevmob.keepmoney.ui.accounts.detail;

import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpView;

import java.util.List;

public interface AccountsDetailMvpView extends BaseMvpView {
    void showTransactions(List<TransactionModels> transactionModels);
    void showTransactionsEmpty();
    void updateAdapterAccounts();
    void deleteItemInAdapter(TransactionModels transactionModels);
    void startAddTransactionActivity(long accountId);
    void startEditTransactionActivity(long accountId, long transactionId);
    void showAccountsBalanceToolbar(double balance);
}