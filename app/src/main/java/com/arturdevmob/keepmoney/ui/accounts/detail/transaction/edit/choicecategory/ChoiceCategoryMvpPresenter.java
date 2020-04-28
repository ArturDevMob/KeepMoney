package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory;

import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpPresenter;

public interface ChoiceCategoryMvpPresenter<V extends ChoiceCategoryMvpView> extends BaseMvpPresenter<V> {
    void loadCategoriesExpense();
    void loadCategoriesIncome();
    void loadLastSelectedCategory(long categoryId);
    void applyCategory(CategoryModels categoryModels);
}
