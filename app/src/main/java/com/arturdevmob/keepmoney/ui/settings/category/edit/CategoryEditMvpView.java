package com.arturdevmob.keepmoney.ui.settings.category.edit;

import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseMvpView;

import java.util.List;

public interface CategoryEditMvpView extends BaseMvpView {
    void setCategoryObjectModel(CategoryModels category);
    void showCategoryImages(List<String> pathsToImagesCategories);
    void finishActivity();
}
