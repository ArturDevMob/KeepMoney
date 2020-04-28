package com.arturdevmob.keepmoney.ui.settings.category.list;

import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpView;

import java.util.List;

public interface CategoryListMvpView extends BaseMvpView {
    void showCategories(List<CategoryModels> categoryModelsList);
    void showCategoriesEmpty();
    void startActivityAddCategory(boolean isExpense);
    void startActivityEditCategory(boolean isExpense, long categoryId);
    void removeCategoryFromAdapter(CategoryModels categoryModels);
}
