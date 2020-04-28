package com.arturdevmob.keepmoney.ui.accounts.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.Utils;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.ui.accounts.detail.AccountsDetailFragment;
import com.arturdevmob.keepmoney.ui.accounts.edit.AccountsEditActivity;
import com.arturdevmob.keepmoney.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountsListFragment extends BaseFragment implements AccountsListMvpView {
    public static final String TAG = "AccountsListFragment";
    public static final int REQUEST_CODE_ACCOUNT_EDIT = 100;

    @Inject
    AccountsListMvpPresenter<AccountsListMvpView> mPresenter;

    @Inject
    AccountsListAdapter mAccountsListAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.include_empty_category)
    View mAccountsEmpty;

    @BindView(R.id.include_toolbar)
    Toolbar mToolbar;

    TextView mTitleToolbarBalanceTextView;

    TextView mGeneralBalanceAllAccountsTextView;


    public static AccountsListFragment newInstance() {
        return new AccountsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(AccountsListFragment.this);

        View view = inflater.inflate(R.layout.fragment_accounts_list, container, false);

        mPresenter.attachView(AccountsListFragment.this);

        ButterKnife.bind(this, view);

        setupToolbar();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mAccountsListAdapter.setCallback(implementAdapterCallback());
        mRecyclerView.setAdapter(mAccountsListAdapter);
        mPresenter.loadAccounts();

        return view;
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);

        View view = getLayoutInflater().inflate(R.layout.toolbar_balance_view, mToolbar);

        mTitleToolbarBalanceTextView = view.findViewById(R.id.title_text_view);
        mTitleToolbarBalanceTextView.setText(R.string.general_balance);
        mGeneralBalanceAllAccountsTextView = view.findViewById(R.id.balance_text_view);

        mPresenter.loadAccountsBalanceToolbar();

        setHasOptionsMenu(true);
    }

    @Override
    public void showAccountsBalanceToolbar(double balance, CurrencyType currencyType) {
        mGeneralBalanceAllAccountsTextView.setText(Utils.amountFormat(balance, currencyType));

        mGeneralBalanceAllAccountsTextView.setOnClickListener(v -> {
           mPresenter.onClickGeneralBalanceToolbar();
        });
    }

    @Override
    public void showAccountsBalanceErrorToolbar() {
        mGeneralBalanceAllAccountsTextView.setTextSize(12);
        mGeneralBalanceAllAccountsTextView.setText(R.string.error_show);

        mGeneralBalanceAllAccountsTextView.setOnClickListener(v -> {
            mPresenter.onClickGeneralBalanceErrorToolbar();
        });
    }

    @Override
    public void showDialogWithGeneralBalance() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title_general_balance)
                .setNeutralButton(R.string.accept, null)
                .setMessage(R.string.dialog_description_general_balance)
                .create()
                .show();
    }

    @Override
    public void showDialogWithErrorGeneralBalance() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title_general_balance_error)
                .setNeutralButton(R.string.accept, null)
                .setMessage(R.string.dialog_description_general_balance_error)
                .create()
                .show();
    }

    private AccountsListAdapter.OnEventAdapter implementAdapterCallback() {
        AccountsListAdapter.OnEventAdapter callback = new AccountsListAdapter.OnEventAdapter() {
            @Override
            public void onClickDeleteAccount(AccountModels accountModels) {
                mPresenter.deleteAccounts(accountModels);
            }

            @Override
            public void onClickAccount(AccountModels accountModels) {
                mPresenter.onClickAccount(accountModels);
            }

            @Override
            public void onLongClickAccount(AccountModels accountModels) {
                mPresenter.longClickAccount(accountModels);
            }
        };

        return callback;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mPresenter.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mPresenter = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_add, menu);
        inflater.inflate(R.menu.toolbar_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_action:
                mPresenter.addAccount();
            break;

            case R.id.edit_list_action:
                mPresenter.editListAccounts();
            break;
        }
        return true;
    }

    @Override
    public void showAccounts(List<AccountModels> accounts) {
        mAccountsEmpty.setVisibility(View.GONE);

        mAccountsListAdapter.setData(accounts);
    }

    @Override
    public void showAccountsEmpty() {
        mAccountsEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void startAccountEditActivity(AccountModels accountModels) {
        Intent intent;

        if (accountModels == null) {
            intent = AccountsEditActivity.newIntent(getBaseActivity());
        } else {
            intent = AccountsEditActivity.newIntent(getBaseActivity(), accountModels.getId());
        }

        startActivityForResult(intent, REQUEST_CODE_ACCOUNT_EDIT);
    }

    @Override
    public void startDetailFragment(AccountModels accountModels) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, AccountsDetailFragment.newInstance(accountModels.getId()), AccountsDetailFragment.TAG)
                .addToBackStack(AccountsListFragment.TAG)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Результат добавления нового счета
        if (requestCode == REQUEST_CODE_ACCOUNT_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                mPresenter.loadAccounts();
                mPresenter.loadAccountsBalanceToolbar();
                showSnackBar(R.string.successful_add_account);
            }
        }
    }

    @Override
    public void showHideControlElements() {
        mAccountsListAdapter.showHideControlElements();
    }

    @Override
    public void showAlertDialogForAccount(AccountModels accountModels) {
        String[] keysAdapter = getResources().getStringArray(R.array.management_account);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, keysAdapter);

        DialogInterface.OnClickListener callback = (dialog, which) -> {
            switch (which) {
                case 0:
                    mPresenter.editAccount(accountModels);
                    break;

                case 1:
                    mPresenter.deleteAccounts(accountModels);
                    break;
            }
            dialog.dismiss();
        };

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_dialog_account)
                .setAdapter(adapter, callback)
                .create()
                .show();
    }

    @Override
    public void updateAdapterAccounts() {
        if (mAccountsListAdapter.getData().size() == 0) {
            showAccountsEmpty();
        }

        mAccountsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItemInAdapter(AccountModels accountModels) {
        mAccountsListAdapter.getData().remove(accountModels);
    }

    @OnClick(R.id.create_account_button)
    public void onCreateAccountButtonClick() {
        mPresenter.addAccount();
    }
}
