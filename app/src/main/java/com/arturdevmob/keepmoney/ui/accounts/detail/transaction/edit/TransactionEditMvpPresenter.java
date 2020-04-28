package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit;

import com.arturdevmob.keepmoney.ui.base.BaseMvpPresenter;

public interface TransactionEditMvpPresenter<V extends TransactionEditMvpView> extends BaseMvpPresenter<V> {
    void loadTransaction(long transactionId);
    void addTransaction(boolean isEditMode);
    void selectingDateTime();
    void loadCurrencyType(long accountId);

    void selectingCategory(boolean isExpenseCategory);
    void selectingCategory(boolean isExpenseCategory, long categoryId);
    void selectedCategory(long categoryId);
    void deselectedCategory();
}
