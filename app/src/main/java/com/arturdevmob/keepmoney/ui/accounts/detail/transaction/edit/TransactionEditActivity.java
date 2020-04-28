package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.Utils;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionType;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory.ChoiceCategoryActivity;
import com.arturdevmob.keepmoney.ui.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

public class TransactionEditActivity extends BaseActivity implements TransactionEditMvpView {
    public static final String EXTRA_DATA_ACCOUNT_ID = "extra_data_account_id";
    public static final String EXTRA_DATA_TRANSACTION_ID = "extra_data_transaction_id";
    public static final String EXTRA_DATA_SELECTED_CATEGORY_ID = "selected_category_id";
    public static final int REQUEST_CODE_SET_CATEGORY = 666;

    @Inject TransactionEditMvpPresenter<TransactionEditMvpView> mPresenter;

    @BindView(R.id.include_toolbar) Toolbar mToolbar;
    @BindView(R.id.title_edit_text) EditText mTitleEditText;
    @BindView(R.id.description_edit_text) EditText mDescriptionEditText;
    @BindView(R.id.amount_edit_text) EditText mAmountEditText;
    @BindView(R.id.category_text_view) TextView mCategoryTextView;
    @BindView(R.id.date_time_text_view) TextView mDateTimeTextView;
    @BindView(R.id.currency_type_text_view) TextView mCurrencyTypeTextView;
    TabLayout mTransactionTypeTabLayout;
    private long mCategoryId;

    // Создание новой транзакции в счете
    public static Intent newIntent(Context context, long accountId) {
        Intent intent = new Intent(context, TransactionEditActivity.class);
        intent.putExtra(EXTRA_DATA_ACCOUNT_ID, accountId);

        return intent;
    }

    // Редактирование транзакции в счете
    public static Intent newIntent(Context context, long accountId, long transactionId) {
        Intent intent = new Intent(context, TransactionEditActivity.class);
        intent.putExtra(EXTRA_DATA_ACCOUNT_ID, accountId);
        intent.putExtra(EXTRA_DATA_TRANSACTION_ID, transactionId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_edit);

        getActivityComponent().inject(this);

        ButterKnife.bind(this);

        mPresenter.attachView(this);

        setupToolbar();

        if (isEditMode()) {
            mPresenter.loadTransaction(getIdTransactionFromIntent());
        } else {
            mPresenter.loadCurrencyType(getIdAccountFromIntent());
        }
    }

    @Override
    public void setupToolbar() {
        View view = getLayoutInflater().inflate(R.layout.income_expense_tab_layout, mToolbar);
        mTransactionTypeTabLayout = view.findViewById(R.id.type_tab_layout);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener((v) -> onBackPressed());
    }

    private boolean isEditMode() {
        Bundle extras = getIntent().getExtras();

        if (extras.containsKey(EXTRA_DATA_TRANSACTION_ID)) {
            return true;
        }

        return false;
    }

    private long getIdTransactionFromIntent() {
        Bundle intentData = getIntent().getExtras();
        return intentData.getLong(EXTRA_DATA_TRANSACTION_ID);
    }

    private long getIdAccountFromIntent() {
        Bundle intentData = getIntent().getExtras();
        return intentData.getLong(EXTRA_DATA_ACCOUNT_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_action:
                mPresenter.addTransaction(isEditMode());
            break;
        }
        return true;
    }

    @Override
    public void showDialogChoiceDateTime() {
        DateTimeDialog dialog = new DateTimeDialog(this);
        dialog.setCallback((date -> {
            mDateTimeTextView.setText(Utils.dateLongFormatForString(date));
        }));
    }

    @Override
    public void startActivityChoiceCategory(boolean isExpenseCategory) {
        startActivityForResult(ChoiceCategoryActivity.newIntent(this, isExpenseCategory), REQUEST_CODE_SET_CATEGORY);
    }

    @Override
    public void startActivityChangeCategory(boolean isExpenseCategory, long categoryId) {
        startActivityForResult(ChoiceCategoryActivity.newIntent(this, isExpenseCategory, categoryId), REQUEST_CODE_SET_CATEGORY);
    }

    @Override
    public void setCategoryIdInField(long categoryId) {
        mCategoryId = categoryId;
    }

    @Override
    public void showCategoryTitleTextView(String categoryTitle) {
        mCategoryTextView.setText(categoryTitle);
    }

    @Override
    public void showNotCategoryTitleTextView() {
        mCategoryTextView.setText(R.string.not_selected);
    }

    @Override
    public void setCurrencyTypeTextView(String currencyType) {
        mCurrencyTypeTextView.setText(currencyType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SET_CATEGORY && resultCode == RESULT_OK) {
            if (data == null) {
                mPresenter.deselectedCategory();
            } else {
                long categoryId = data.getExtras().getLong(EXTRA_DATA_SELECTED_CATEGORY_ID);
                mPresenter.selectedCategory(categoryId);
            }
        }
    }

    @Override
    public void finishActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public TransactionModels getDataTransaction() {
        TransactionModels transactionModels = new TransactionModels();
        transactionModels.setId(getIdTransactionFromIntent());
        transactionModels.setAccountId(getIdAccountFromIntent());
        transactionModels.setCategoryId(mCategoryId);
        transactionModels.setTitle(mTitleEditText.getText().toString());
        transactionModels.setDescription(mDescriptionEditText.getText().toString());
        transactionModels.setAmount(Utils.amountParseFromString(mAmountEditText.getText().toString()));
        transactionModels.setTransactionType(TransactionType.getById(mTransactionTypeTabLayout.getSelectedTabPosition()));
        transactionModels.setCreateDate(Utils.dateLongParseFromString(mDateTimeTextView.getText().toString()));
        transactionModels.setAccountModels(null); // Заполняет презентер
        transactionModels.setCategoryModels(null); // Заполняет презентер

        return transactionModels;
    }

    @Override
    public void fillFieldsEdit(TransactionModels transactionModels) {
        mTitleEditText.setText(transactionModels.getTitle());
        mDescriptionEditText.setText(transactionModels.getDescription());
        mAmountEditText.setText(Utils.amountFormatNoCurrencyType(transactionModels.getAmount()));
        mDateTimeTextView.setText(Utils.dateLongFormatForString(transactionModels.getCreateDate()));

        if (transactionModels.getTransactionType() == TransactionType.EXPENSE) {
            mTransactionTypeTabLayout.getTabAt(0).select();
        } else if (transactionModels.getTransactionType() == TransactionType.INCOME) {
            mTransactionTypeTabLayout.getTabAt(1).select();
        }

        if (transactionModels.getCategoryModels() == null) {
            this.setCategoryIdInField(0);
            this.showNotCategoryTitleTextView();
        } else {
            this.setCategoryIdInField(transactionModels.getCategoryId());
            this.showCategoryTitleTextView(transactionModels.getCategoryModels().getTitle());
        }
    }

    @OnClick(R.id.date_time_text_view)
    public void onClickDateTimeTextView() {
        mPresenter.selectingDateTime();
    }

    @OnClick(R.id.category_text_view)
    public void onClickCategoryTextView() {
        boolean isExpenseCategory = mTransactionTypeTabLayout.getSelectedTabPosition() == 0 ? true : false;

        if (mCategoryId == 0) {
            mPresenter.selectingCategory(isExpenseCategory);
        } else {
            mPresenter.selectingCategory(isExpenseCategory, mCategoryId);
        }
    }
}