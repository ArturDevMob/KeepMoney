package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit;

import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpView;

public interface TransactionEditMvpView extends BaseMvpView {
    void showDialogChoiceDateTime();
    void finishActivity();
    TransactionModels getDataTransaction();
    void fillFieldsEdit(TransactionModels transactionModels);
    void startActivityChoiceCategory(boolean isExpenseCategory);
    void startActivityChangeCategory(boolean isExpenseCategory, long categoryId);
    void setCategoryIdInField(long categoryId);
    void showCategoryTitleTextView(String categoryTitle);
    void showNotCategoryTitleTextView();
    void setCurrencyTypeTextView(String currencyType);
}
