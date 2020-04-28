package com.arturdevmob.keepmoney.ui.settings.category.list;

import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpPresenter;

public interface CategoryListMvpPresenter<V extends CategoryListMvpView> extends BaseMvpPresenter<V> {
    void addCategoryIncome();
    void addCategoryExpense();
    void editCategory(CategoryModels categoryModels);
    void loadCategoriesExpense();
    void loadCategoriesIncome();
    void deleteCategory(CategoryModels categoryModels);
}
