package com.arturdevmob.keepmoney.ui.accounts.edit;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpView;

import java.util.List;

public interface AccountsListEditMvpView extends BaseMvpView {
    void finishActivity();
    void setDataToFieldsAndObjectFromModel(AccountModels accountModels);
    void showDialogSetCurrencyType(List<String> currencyTypeList);
    void setCurrencyTypeTextView(String currencyType);
}
