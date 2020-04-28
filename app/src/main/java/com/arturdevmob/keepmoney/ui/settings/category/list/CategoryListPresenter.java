package com.arturdevmob.keepmoney.ui.settings.category.list;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class CategoryListPresenter<V extends CategoryListMvpView> extends BasePresenter<V> implements CategoryListMvpPresenter<V> {
    @Inject
    public CategoryListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void addCategoryIncome() {
        getView().startActivityAddCategory(false);
    }

    @Override
    public void addCategoryExpense() {
        getView().startActivityAddCategory(true);
    }

    @Override
    public void editCategory(CategoryModels categoryModels) {
        getView().startActivityEditCategory(categoryModels.isExpense(), categoryModels.getId());
    }

    @Override
    public void loadCategoriesExpense() {
        List<CategoryModels> categoryModelsList = getDataManager().getAllCategoryExpense();

        if (categoryModelsList.size() == 0) {
            getView().showCategoriesEmpty();
        } else {
            getView().showCategories(categoryModelsList);
        }
    }

    @Override
    public void loadCategoriesIncome() {
        List<CategoryModels> categoryModelsList = getDataManager().getAllCategoryIncome();

        if (categoryModelsList.size() == 0) {
            getView().showCategoriesEmpty();
        } else {
            getView().showCategories(categoryModelsList);
        }
    }

    @Override
    public void deleteCategory(CategoryModels categoryModels) {
        getView().removeCategoryFromAdapter(categoryModels);

        getDataManager().deleteCategory(categoryModels);

        int count;
        if (categoryModels.isExpense()) {
            count = getDataManager().getAllCategoryExpense().size();
        } else {
            count = getDataManager().getAllCategoryIncome().size();
        }

        if (count == 0) {
            getView().showCategoriesEmpty();
        }
    }
}
