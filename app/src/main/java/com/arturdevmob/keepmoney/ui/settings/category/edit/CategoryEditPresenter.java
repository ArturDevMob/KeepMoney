package com.arturdevmob.keepmoney.ui.settings.category.edit;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class CategoryEditPresenter<V extends CategoryEditMvpView> extends BasePresenter<V> implements CategoryEditMvpPresenter<V> {
    @Inject
    public CategoryEditPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadCategory(long categoryId) {
        CategoryModels category = getDataManager().getCategoryById(categoryId);

        getView().setCategoryObjectModel(category);
    }

    @Override
    public void loadCategoryImages() {
        List<String> pathsToImagesCategories = getDataManager().getPathsToImagesCategory();

        getView().showCategoryImages(pathsToImagesCategories);
    }

    @Override
    public void addCategory(CategoryModels category) {
        if (verificationCategory(category)) {
            getDataManager().insertCategory(category);
            getView().finishActivity();
        } else {
            getView().showSnackBar("Не удалось добавить категорию");
        }
    }

    @Override
    public void editCategory(CategoryModels category) {
        if (verificationCategory(category)) {
            getDataManager().updateCategory(category);
            getView().finishActivity();
        } else {
            getView().showSnackBar("Не удалось отредактировать категорию");
        }
    }

    private boolean verificationCategory(CategoryModels categoryModels) {
        if (categoryModels == null) return false;
        if (categoryModels.getTitle().isEmpty()) return false;

        return true;
    }
}
