package com.arturdevmob.keepmoney.ui.accounts.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.Utils;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.TransactionAdapter;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.TransactionEditActivity;
import com.arturdevmob.keepmoney.ui.base.BaseFragment;

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

public class AccountsDetailFragment extends BaseFragment implements AccountsDetailMvpView {
    public static final String TAG = "AccountsDetailFragment";
    public static final String ARGS_ACCOUNT_ID = "account_id";
    public static final int REQUEST_CODE_ADD_EDIT_TRANSACTION = 111;

    public static AccountsDetailFragment newInstance(long accountId) {
        Bundle args = new Bundle();
        args.putLong(ARGS_ACCOUNT_ID, accountId);

        AccountsDetailFragment fragment = new AccountsDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Inject AccountsDetailMvpPresenter<AccountsDetailMvpView> mPresenter;
    @Inject TransactionAdapter mTransactionAdapter;

    @BindView(R.id.include_toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.include_transaction_empty) View mTransactionEmpty;
    TextView mTitleToolbarBalanceTextView;
    TextView mBalanceAccountTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);

        View view = inflater.inflate(R.layout.fragment_accounts_detail, container, false);

        mPresenter.attachView(this);

        ButterKnife.bind(this, view);

        setupToolbar();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mTransactionAdapter);
        mTransactionAdapter.setCallback(setCallbackTransactionAdapter());
        mPresenter.loadAccountTransactions(getAccountIdFromArgsFragment());

        return view;
    }

    private TransactionAdapter.OnEventAdapter setCallbackTransactionAdapter() {
        return new TransactionAdapter.OnEventAdapter() {
            @Override
            public void onClickDeleteTransactionButton(TransactionModels transactionModels) {
                mPresenter.deleteTransaction(transactionModels);
                mPresenter.loadAccountBalanceToolbar(getAccountIdFromArgsFragment());
            }

            @Override
            public void onClickEditTransactionButton(TransactionModels transactionModels) {
                mPresenter.editTransaction(transactionModels);
            }
        };
    }

    private long getAccountIdFromArgsFragment() {
        return getArguments().getLong(ARGS_ACCOUNT_ID);
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);

        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        mToolbar.setNavigationOnClickListener((v) -> getBaseActivity().onBackPressed());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_balance_view, mToolbar);
        mTitleToolbarBalanceTextView = view.findViewById(R.id.title_text_view);
        mTitleToolbarBalanceTextView.setText(R.string.account_balance);
        mBalanceAccountTextView = view.findViewById(R.id.balance_text_view);

        mPresenter.loadAccountBalanceToolbar(getAccountIdFromArgsFragment());
    }

    @Override
    public void showAccountsBalanceToolbar(double balance) {
        mBalanceAccountTextView.setText(Utils.amountFormat(balance, CurrencyType.RUB));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_action:
                mPresenter.addTransaction(getAccountIdFromArgsFragment());
            break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Если транзакция успешно добавлена/отредактирована, обновляем адаптер
        if (requestCode == REQUEST_CODE_ADD_EDIT_TRANSACTION) {
            if (resultCode == getActivity().RESULT_OK) {
                mPresenter.loadAccountTransactions(getAccountIdFromArgsFragment());
                mPresenter.loadAccountBalanceToolbar(getAccountIdFromArgsFragment());
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void showTransactions(List<TransactionModels> transactionModels) {
        mTransactionEmpty.setVisibility(View.GONE);

        mTransactionAdapter.setData(transactionModels);
    }

    @Override
    public void showTransactionsEmpty() {
        mTransactionEmpty.setVisibility(View.VISIBLE);
    }

    public void hideTransactionsEmpty() {
        mTransactionEmpty.setVisibility(View.GONE);
    }

    @Override
    public void updateAdapterAccounts() {
        mTransactionAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItemInAdapter(TransactionModels transactionModels) {
        mTransactionAdapter.getData().remove(transactionModels);

        if (mTransactionAdapter.getData().size() == 0) {
            showTransactionsEmpty();
        } else {
            hideTransactionsEmpty();
        }
    }

    @Override
    public void startAddTransactionActivity(long accountId) {
        startActivityForResult(TransactionEditActivity.newIntent(getActivity(), accountId), REQUEST_CODE_ADD_EDIT_TRANSACTION);
    }

    @Override
    public void startEditTransactionActivity(long accountId, long transactionId) {
        startActivityForResult(TransactionEditActivity.newIntent(getActivity(), accountId, transactionId), REQUEST_CODE_ADD_EDIT_TRANSACTION);
    }

    @OnClick(R.id.create_transaction_button)
    public void onClickCreateTransactionButton() {
        mPresenter.addTransaction(getAccountIdFromArgsFragment());
    }
}
