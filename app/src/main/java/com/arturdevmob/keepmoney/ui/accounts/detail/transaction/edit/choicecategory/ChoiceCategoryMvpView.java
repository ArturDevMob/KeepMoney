package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory;

import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpView;

import java.util.List;

public interface ChoiceCategoryMvpView extends BaseMvpView {
    void showCategoryExpense(List<CategoryModels> categoryModelsList);
    void showCategoryIncome(List<CategoryModels> categoryModelsList);
    void finishActivityCategorySelected(CategoryModels categoryModels);
    void finishActivityCategoryNotSelected();
    void setLastSelectedCategory(CategoryModels categoryModels);
}
