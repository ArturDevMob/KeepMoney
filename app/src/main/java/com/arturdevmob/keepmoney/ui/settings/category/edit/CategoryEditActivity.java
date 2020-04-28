package com.arturdevmob.keepmoney.ui.settings.category.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryEditActivity extends BaseActivity implements CategoryEditMvpView {
    public static final String TAG = "CategoryEditActivity";
    public static final String EXTRA_DATA_CATEGORY_ID = "category_id";
    public static final String EXTRA_DATA_CATEGORY_IS_EXPENSE = "category_is_expense";

    private boolean isEditMode = false;

    @Inject CategoryEditMvpPresenter<CategoryEditMvpView> mPresenter;
    @Inject AdapterCategoryImage mAdapterCategoryImage;

    private CategoryModels mCategoryModels;

    @BindView(R.id.include_toolbar) Toolbar mToolbar;
    @BindView(R.id.title_edit_text) EditText mTitleEditText;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    // Интент для создания новой категории
    public static Intent newIntent(Context context, boolean isExpense) {
        Intent intent = new Intent(context, CategoryEditActivity.class);
        intent.putExtra(EXTRA_DATA_CATEGORY_IS_EXPENSE, isExpense);

        return intent;
    }

    // Интент для редактирования категории
    public static Intent newIntent(Context context, boolean isExpense, long categoryId) {
        Intent intent = new Intent(context, CategoryEditActivity.class);
        intent.putExtra(EXTRA_DATA_CATEGORY_IS_EXPENSE, isExpense);
        intent.putExtra(EXTRA_DATA_CATEGORY_ID, categoryId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        getActivityComponent().inject(this);

        mPresenter.attachView(this);

        ButterKnife.bind(this);

        setupToolbar();

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapterCategoryImage);
        mPresenter.loadCategoryImages();

        // Если редактируем существующую категорию, заполняем объект категории и поля для ввода
        getCategoryFromIntent();
        fillInFieldsFromObjectModel();
    }

    private void fillInFieldsFromObjectModel() {
        if (isEditMode && mCategoryModels != null) {
            mTitleEditText.setText(mCategoryModels.getTitle());

            if (mCategoryModels.getImagePath() != null) {
                mAdapterCategoryImage.setSelectedImage(mCategoryModels.getImagePath());
            }
        }
    }

    private void fillObjectModelFromDataFields() {
        if (! isEditMode) {
            mCategoryModels = new CategoryModels();
        }

        mCategoryModels.setParentId(0);
        mCategoryModels.setTitle(mTitleEditText.getText().toString());
        mCategoryModels.setExpense(categoryIsExpense());
        mCategoryModels.setImagePath(mAdapterCategoryImage.getPathSelectedImage());
    }

    private void getCategoryFromIntent() {
        Bundle data = getIntent().getExtras();
        if (data != null && data.containsKey(EXTRA_DATA_CATEGORY_ID)) {
            isEditMode = true;
            mPresenter.loadCategory(data.getLong(EXTRA_DATA_CATEGORY_ID));
        }
    }

    private boolean categoryIsExpense() {
        Bundle data = getIntent().getExtras();
        if (data != null && data.containsKey(EXTRA_DATA_CATEGORY_IS_EXPENSE)) {
            return data.getBoolean(EXTRA_DATA_CATEGORY_IS_EXPENSE);
        }

        return false;
    }

    @Override
    public void setupToolbar() {
        if (isEditMode) {
            mToolbar.setTitle(R.string.changing_category);
        } else {
            if (categoryIsExpense()) {
                mToolbar.setTitle(R.string.new_category_expense);
            } else {
                mToolbar.setTitle(R.string.new_category_income);
            }
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener((v -> onBackPressed()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_action:
                fillObjectModelFromDataFields();

                if (isEditMode) {
                    mPresenter.editCategory(mCategoryModels);
                } else {
                    mPresenter.addCategory(mCategoryModels);
                }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCategoryImages(List<String> pathsToImagesCategories) {
        mAdapterCategoryImage.setData(pathsToImagesCategories);
    }

    @Override
    public void setCategoryObjectModel(CategoryModels category) {
        mCategoryModels = category;
    }

    @Override
    public void finishActivity() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
