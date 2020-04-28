package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;


import java.util.List;

import javax.inject.Inject;

public class ChoiceCategoryPresenter<V extends ChoiceCategoryMvpView> extends BasePresenter<V> implements ChoiceCategoryMvpPresenter<V> {
    @Inject
    public ChoiceCategoryPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void loadCategoriesExpense() {
        List<CategoryModels> categoryModelsList = getDataManager().getAllCategoryExpense();
        getView().showCategoryExpense(categoryModelsList);
    }

    @Override
    public void loadCategoriesIncome() {
        List<CategoryModels> categoryModelsList = getDataManager().getAllCategoryIncome();
        getView().showCategoryIncome(categoryModelsList);
    }

    @Override
    public void loadLastSelectedCategory(long categoryId) {
        CategoryModels categoryModels = getDataManager().getCategoryById(categoryId);
        getView().setLastSelectedCategory(categoryModels);
    }

    @Override
    public void applyCategory(CategoryModels categoryModels) {
        if (categoryModels == null) {
            getView().finishActivityCategoryNotSelected();
        } else {
            getView().finishActivityCategorySelected(categoryModels);
        }
    }
}
