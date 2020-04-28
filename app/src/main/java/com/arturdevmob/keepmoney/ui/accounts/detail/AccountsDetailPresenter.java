package com.arturdevmob.keepmoney.ui.accounts.detail;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class AccountsDetailPresenter<V extends AccountsDetailMvpView> extends BasePresenter<V> implements AccountsDetailMvpPresenter<V> {
    @Inject
    public AccountsDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadAccountTransactions(long accountId) {
        List<TransactionModels> transactionModels = getDataManager().getAllTransactionOfAccountById(accountId);

        if (transactionModels.size() == 0) {
            getView().showTransactionsEmpty();
        } else {
            getView().showTransactions(transactionModels);
            getView().updateAdapterAccounts();
        }
    }

    @Override
    public void deleteTransaction(TransactionModels transactionModels) {
        getView().deleteItemInAdapter(transactionModels);
        getView().updateAdapterAccounts();
        getView().showSnackBar("Транзакция #" + transactionModels.getId() + " успешно удалена!");

        getDataManager().deleteTransaction(transactionModels);
    }

    @Override
    public void addTransaction(long accountId) {
        getView().startAddTransactionActivity(accountId);
    }

    @Override
    public void editTransaction(TransactionModels transactionModels) {
        long accountId = transactionModels.getAccountId();
        long transactionId = transactionModels.getId();

        getView().startEditTransactionActivity(accountId, transactionId);
    }

    @Override
    public void loadAccountBalanceToolbar(long accountId) {
        AccountModels account = getDataManager().getAccountById(accountId);
        double balance = account.getCurrentBalance();

        getView().showAccountsBalanceToolbar(balance);
    }
}
