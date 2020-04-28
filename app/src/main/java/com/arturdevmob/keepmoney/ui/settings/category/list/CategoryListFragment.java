package com.arturdevmob.keepmoney.ui.settings.category.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.ui.base.BaseFragment;
import com.arturdevmob.keepmoney.ui.settings.category.edit.CategoryEditActivity;
import com.google.android.material.tabs.TabLayout;

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

public class CategoryListFragment extends BaseFragment implements CategoryListMvpView {
    public static final String TAG = "CategoryListFragment";
    public static final int REQUEST_CODE_CATEGORY_EDIT = 10;

    @Inject CategoryListMvpPresenter<CategoryListMvpView> mPresenter;
    @Inject CategoryAdapter mCategoryAdapter;

    @BindView(R.id.include_toolbar) Toolbar mToolbar;
    @BindView(R.id.type_tab_layout) TabLayout mCategoryTypeTabLayout;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.include_empty_category) View mCategoriesEmptyView;

    public static CategoryListFragment newInstance() {
        return new CategoryListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        getActivityComponent().inject(this);

        mPresenter.attachView(this);

        ButterKnife.bind(this, view);

        setupToolbar();

        mCategoryTypeTabLayout.addOnTabSelectedListener(createListenerTabLayout());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setCallback(createCallbackAdapter());

        mPresenter.loadCategoriesExpense();

        return view;
    }

    private CategoryAdapter.OnEventAdapterForEditView createCallbackAdapter() {
        return new CategoryAdapter.OnEventAdapterForEditView() {
            @Override
            public void onDeleteCategory(CategoryModels categoryModels) {
                mPresenter.deleteCategory(categoryModels);
            }

            @Override
            public void onEditCategory(CategoryModels categoryModels) {
                mPresenter.editCategory(categoryModels);
            }
        };
    }

    @Override
    public void setupToolbar() {
        mToolbar.setTitle(R.string.categories);

        getBaseActivity().setSupportActionBar(mToolbar);
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener((v -> getBaseActivity().onBackPressed()));

        setHasOptionsMenu(true);
    }

    private TabLayout.OnTabSelectedListener createListenerTabLayout() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mPresenter.loadCategoriesExpense();
                } else if (tab.getPosition() == 1) {
                    mPresenter.loadCategoriesIncome();
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        };
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_action:
                if (mCategoryTypeTabLayout.getSelectedTabPosition() == 0) {
                    mPresenter.addCategoryExpense();
                } else if (mCategoryTypeTabLayout.getSelectedTabPosition() == 1) {
                    mPresenter.addCategoryIncome();
                }
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCategories(List<CategoryModels> categoryModelsList) {
        mCategoriesEmptyView.setVisibility(View.GONE);

        mCategoryAdapter.setData(categoryModelsList);
    }

    @Override
    public void showCategoriesEmpty() {
        mCategoryAdapter.clearData();
        mCategoriesEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void startActivityAddCategory(boolean isExpense) {
        Intent intent = CategoryEditActivity.newIntent(getActivity(), isExpense);
        startActivityForResult(intent, REQUEST_CODE_CATEGORY_EDIT);
    }

    @Override
    public void startActivityEditCategory(boolean isExpense, long categoryId) {
        Intent intent = CategoryEditActivity.newIntent(getActivity(), isExpense, categoryId);
        startActivityForResult(intent, REQUEST_CODE_CATEGORY_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CATEGORY_EDIT && resultCode == getActivity().RESULT_OK) {
            if (mCategoryTypeTabLayout.getSelectedTabPosition() == 0) {
                mPresenter.loadCategoriesExpense();
            } else {
                mPresenter.loadCategoriesIncome();
            }
        }
    }

    @Override
    public void removeCategoryFromAdapter(CategoryModels categoryModels) {
        mCategoryAdapter.getData().remove(categoryModels);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.create_category_button)
    public void onClickCreateCategoryButton() {
        if (mCategoryTypeTabLayout.getSelectedTabPosition() == 0) {
            mPresenter.addCategoryExpense();
        } else if (mCategoryTypeTabLayout.getSelectedTabPosition() == 1) {
            mPresenter.addCategoryIncome();
        }
    }
}
