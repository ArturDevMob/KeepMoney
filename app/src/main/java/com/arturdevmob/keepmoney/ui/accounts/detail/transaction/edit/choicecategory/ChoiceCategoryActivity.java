package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseActivity;
import com.arturdevmob.keepmoney.ui.settings.category.list.CategoryAdapter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoiceCategoryActivity extends BaseActivity implements ChoiceCategoryMvpView {
    public static final String TAG = "ChoiceCategoryActivity";
    public static final String EXTRA_DATA_SELECTED_CATEGORY_ID = "selected_category_id";
    public static final String EXTRA_DATA_SELECTED_CATEGORY_TYPE = "category_type";
    public static final String EXTRA_DATA_SELECTED_LAST_CATEGORY_ID = "last_category_id";

    public static Intent newIntent(Context context, boolean isExpenseCategory) {
        Intent intent = new Intent(context, ChoiceCategoryActivity.class);
        intent.putExtra(EXTRA_DATA_SELECTED_CATEGORY_TYPE, isExpenseCategory);

        return intent;
    }

    public static Intent newIntent(Context context, boolean isExpenseCategory, long categoryId) {
        Intent intent = new Intent(context, ChoiceCategoryActivity.class);
        intent.putExtra(EXTRA_DATA_SELECTED_CATEGORY_TYPE, isExpenseCategory);
        intent.putExtra(EXTRA_DATA_SELECTED_LAST_CATEGORY_ID, categoryId);

        return intent;
    }

    @Inject ChoiceCategoryMvpPresenter<ChoiceCategoryMvpView> mPresenter;
    @Inject CategoryAdapter mCategoryAdapter;

    @BindView(R.id.include_toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_category_list);

        getActivityComponent().inject(this);

        mPresenter.attachView(this);

        ButterKnife.bind(this);

        setupToolbar();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mCategoryAdapter);

        if (isExpenseCategory()) {
            mPresenter.loadCategoriesExpense();
        } else {
            mPresenter.loadCategoriesIncome();
        }

        setLastSelectedCategoryFromIntent();
    }

    private boolean isExpenseCategory() {
        return getIntent().getExtras().getBoolean(EXTRA_DATA_SELECTED_CATEGORY_TYPE);
    }

    private void setLastSelectedCategoryFromIntent() {
        Bundle extras = getIntent().getExtras();

        if (extras.containsKey(EXTRA_DATA_SELECTED_LAST_CATEGORY_ID)) {
            mPresenter.loadLastSelectedCategory(extras.getLong(EXTRA_DATA_SELECTED_LAST_CATEGORY_ID));
        }
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener((v -> mPresenter.applyCategory(null)));
    }

    @OnClick(R.id.cancel_selected_category_text_view)
    public void onClickCancelSelectedCategoryTextView() {
        mCategoryAdapter.setSelectedCategory(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_apply, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.apply_action:
                mPresenter.applyCategory(mCategoryAdapter.getSelectedCategory());
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCategoryExpense(List<CategoryModels> categoryModelsList) {
        mCategoryAdapter.setData(categoryModelsList);
    }

    @Override
    public void showCategoryIncome(List<CategoryModels> categoryModelsList) {
        mCategoryAdapter.setData(categoryModelsList);
    }

    @Override
    public void finishActivityCategorySelected(CategoryModels categoryModels) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DATA_SELECTED_CATEGORY_ID, categoryModels.getId());

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void finishActivityCategoryNotSelected() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setLastSelectedCategory(CategoryModels categoryModels) {
        mCategoryAdapter.setSelectedCategory(categoryModels);
    }
}
