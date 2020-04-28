package com.arturdevmob.keepmoney.ui.accounts.list;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class AccountsListPresenter<V extends AccountsListMvpView> extends BasePresenter<V> implements AccountsListMvpPresenter<V> {
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public AccountsListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void detachView() {
        super.detachView();

        mCompositeDisposable.dispose();
    }

    @Override
    public void loadAccounts() {
        List<AccountModels> accounts = getDataManager().getAllAccounts();
        if (accounts.isEmpty()) {
            getView().showAccountsEmpty();
        } else {
            getView().showAccounts(accounts);
        }
        getView().updateAdapterAccounts();
    }

    @Override
    public void addAccount() {
        getView().startAccountEditActivity(null);
    }

    @Override
    public void editAccount(AccountModels accountModels) {
        getView().startAccountEditActivity(accountModels);
    }

    @Override
    public void editListAccounts() {
        getView().showHideControlElements();
    }

    @Override
    public void onClickAccount(AccountModels accountModels) {
        getView().startDetailFragment(accountModels);
    }

    @Override
    public void deleteAccounts(AccountModels accountModels) {
        getDataManager().deleteAccount(accountModels);

        getView().deleteItemInAdapter(accountModels);
        getView().updateAdapterAccounts();

        this.loadAccountsBalanceToolbar();
    }

    @Override
    public void loadAccountsBalanceToolbar() {
        mCompositeDisposable.add(Observable.fromCallable(() -> getDataManager().convertAmountToDefaultCurrencyType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aDouble) -> {
                    CurrencyType defaultCurrencyType = getDataManager().getDefaultCurrencyType();
                    getView().showAccountsBalanceToolbar(aDouble, defaultCurrencyType);
                }, (throwable) -> {
                    getView().showAccountsBalanceErrorToolbar();
                })
        );
    }

    @Override
    public void onClickGeneralBalanceToolbar() {
        getView().showDialogWithGeneralBalance();
    }

    @Override
    public void onClickGeneralBalanceErrorToolbar() {
        getView().showDialogWithErrorGeneralBalance();
    }

    @Override
    public void longClickAccount(AccountModels accountModels) {
        getView().showAlertDialogForAccount(accountModels);
    }
}
