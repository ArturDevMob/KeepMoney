package com.arturdevmob.keepmoney.ui.accounts.edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.Utils;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.ui.base.BaseActivity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountsEditActivity extends BaseActivity implements AccountsListEditMvpView {
    public static final String EXTRA_DATA_ID = "extra_account_id";

    @Inject
    AccountsEditMvpPresenter<AccountsListEditMvpView> mPresenter;

    private AccountModels mAccountModels;
    private boolean isEditMode = false;

    @BindView(R.id.include_toolbar) Toolbar mToolbar;
    @BindView(R.id.title_edit_text) EditText mTitleEditText;
    @BindView(R.id.opening_balance_edit_text) EditText mOpeningBalanceEditText;
    @BindView(R.id.currency_type_text_view) TextView mCurrencyTypeTextView;

    public static Intent newIntent(Context context) {
        return new Intent(context, AccountsEditActivity.class);
    }

    public static Intent newIntent(Context context, long accountId) {
        Intent intent = new Intent(context, AccountsEditActivity.class);
        intent.putExtra(EXTRA_DATA_ID, accountId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_edit);

        ButterKnife.bind(this);

        getActivityComponent().inject(AccountsEditActivity.this);

        mPresenter.attachView(this);

        setupToolbar();

        Bundle extraData = getIntent().getExtras();
        if (extraData != null && extraData.containsKey(EXTRA_DATA_ID)) {
            isEditMode = true;
            mPresenter.loadAccount(extraData.getLong(EXTRA_DATA_ID));
        } else {
            mPresenter.loadDefaultCurrencyType();
        }
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener((v -> onBackPressed()));
    }

    @OnClick(R.id.currency_type_text_view)
    public void onClickCurrencyTypeTextView() {
        mPresenter.setCurrencyType(isEditMode);
    }

    private AccountModels createModelFromDataFields() {
        if (mTitleEditText.getText().toString().isEmpty()) return null;
        if (mOpeningBalanceEditText.getText().toString().isEmpty()) return null;
        if (mCurrencyTypeTextView.getText().toString().isEmpty()) return null;

        if (mAccountModels == null && !isEditMode) {
            mAccountModels = new AccountModels();
            mAccountModels.setCreateDate(new Date().getTime());
        }

        mAccountModels.setTitle(mTitleEditText.getText().toString());
        mAccountModels.setOpeningBalance(Utils.amountParseFromString(mOpeningBalanceEditText.getText().toString()));
        mAccountModels.setCurrencyType(CurrencyType.valueOf(mCurrencyTypeTextView.getText().toString()));

        return mAccountModels;
    }

    @Override
    public void setCurrencyTypeTextView(String currencyType) {
        mCurrencyTypeTextView.setText(currencyType);
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
                mPresenter.addAccount(createModelFromDataFields());
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finishActivity() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void setDataToFieldsAndObjectFromModel(AccountModels accountModels) {
        mAccountModels = accountModels;

        mTitleEditText.setText(accountModels.getTitle());
        mOpeningBalanceEditText.setText(String.valueOf(accountModels.getOpeningBalance()));
        mCurrencyTypeTextView.setText(accountModels.getCurrencyType().name());
    }

    @Override
    public void showDialogSetCurrencyType(List<String> currencyTypeList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyTypeList);
        DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
            String currencyTypeStr = CurrencyType.getById(which).toString();
            mCurrencyTypeTextView.setText(currencyTypeStr);
        };

        new AlertDialog.Builder(this)
                .setTitle(R.string.title_dialog_account_set_currency_type)
                .setAdapter(adapter, onClickListener)
                .create()
                .show();
    }
}
