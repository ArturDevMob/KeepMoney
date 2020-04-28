package com.arturdevmob.keepmoney.ui.accounts.edit;


import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AccountsEditPresenter<V extends AccountsListEditMvpView> extends BasePresenter<V> implements AccountsEditMvpPresenter<V> {
    @Inject
    public AccountsEditPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadAccount(long id) {
        AccountModels accountModels = getDataManager().getAccountById(id);
        getView().setDataToFieldsAndObjectFromModel(accountModels);
    }

    @Override
    public void loadDefaultCurrencyType() {
        String currencyType = getDataManager().getDefaultCurrencyType().name();

        getView().setCurrencyTypeTextView(currencyType);
    }

    @Override
    public void addAccount(AccountModels accountModels) {
        if (accountModels != null) {
            if (accountModels.getId() > 0) {
                getDataManager().updateAccount(accountModels);
            } else {
                getDataManager().insertAccount(accountModels);
            }
            getView().finishActivity();
        } else {
            getView().showSnackBar(R.string.error_create_account);
        }
    }

    @Override
    public void setCurrencyType(boolean isEditMode) {
        if (isEditMode) {
            getView().showSnackBar("Изменить тип валюты счета невозможно!");
        } else {
            List<String> currencyList = new ArrayList<>();

            for (CurrencyType currencyType : CurrencyType.values()) {
                currencyList.add(currencyType.name());
            }

            getView().showDialogSetCurrencyType(currencyList);
        }
    }
}
