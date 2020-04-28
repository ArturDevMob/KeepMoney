package com.arturdevmob.keepmoney.ui.settings.category.edit;

import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpPresenter;

public interface CategoryEditMvpPresenter<V extends CategoryEditMvpView> extends BaseMvpPresenter<V> {
    void loadCategory(long categoryId);
    void loadCategoryImages();
    void addCategory(CategoryModels category);
    void editCategory(CategoryModels category);
}
