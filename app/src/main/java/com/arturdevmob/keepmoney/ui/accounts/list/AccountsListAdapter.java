package com.arturdevmob.keepmoney.ui.accounts.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.Utils;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class AccountsListAdapter extends RecyclerView.Adapter<AccountsListAdapter.AccountsViewHolder> {
    private List<AccountModels> mAccountModelsList;
    private AccountsListAdapter.OnEventAdapter mCallback;
    private boolean isShowsControlElements = false;

    @Inject
    public AccountsListAdapter() {
        mAccountModelsList = new ArrayList<>();
    }

    public void setData(List<AccountModels> accountModels) {
        mAccountModelsList.clear();
        mAccountModelsList.addAll(accountModels);
    }

    public List<AccountModels> getData() {
        return mAccountModelsList;
    }

    public void setCallback(AccountsListAdapter.OnEventAdapter callback) {
        mCallback = callback;
    }

    public void showHideControlElements() {
        if (isShowsControlElements) {
            isShowsControlElements = false;
        } else {
            isShowsControlElements = true;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accounts, parent, false);

        return new AccountsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        AccountModels accountModels = mAccountModelsList.get(position);

        if (accountModels.getCurrentBalance() >= 0) {
            holder.mStatusBalanceFrameLayout.setBackgroundResource(R.drawable.account_balance_positive_color);
        } else {
            holder.mStatusBalanceFrameLayout.setBackgroundResource(R.drawable.account_balance_negative_color);
        }

        holder.mTitleTextView.setText(accountModels.getTitle());
        holder.mBalanceTextView.setText(Utils.amountFormat(accountModels.getCurrentBalance(), accountModels.getCurrencyType()));

        if (isShowsControlElements) {
            holder.mDeleteImageView.setVisibility(View.VISIBLE);
            holder.mArrowImageView.setVisibility(View.GONE);
            holder.mMenuGamburgerImageView.setVisibility(View.VISIBLE);
        } else {
            holder.mDeleteImageView.setVisibility(View.GONE);
            holder.mArrowImageView.setVisibility(View.VISIBLE);
            holder.mMenuGamburgerImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mAccountModelsList.size();
    }

    class AccountsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.status_balance_frame_layout) FrameLayout mStatusBalanceFrameLayout;
        @BindView(R.id.title_text_view) TextView mTitleTextView;
        @BindView(R.id.create_date_text_view) TextView mBalanceTextView;
        @BindView(R.id.arrow_image_view) ImageView mArrowImageView;
        @BindView(R.id.menu_gamburger_image_view) ImageView mMenuGamburgerImageView;
        @BindView(R.id.delete_image_view) ImageView mDeleteImageView;

        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.delete_image_view)
        public void onClickDeleteImage() {
            AccountModels accountModels = mAccountModelsList.get(getAdapterPosition());

            mCallback.onClickDeleteAccount(accountModels);
        }

        @OnClick(R.id.card_view_transaction)
        public void onClickCardView() {
            mCallback.onClickAccount(mAccountModelsList.get(getAdapterPosition()));
        }

        @OnLongClick(R.id.card_view_transaction)
        public void onLongClickCardView() {
            mCallback.onLongClickAccount(mAccountModelsList.get(getAdapterPosition()));
        }
    }

    // Интерфейс для общения с фрагментом
    interface OnEventAdapter {
        void onClickAccount(AccountModels accountModels); // Обычный клик на View счета. Открывает список транзакций
        void onLongClickAccount(AccountModels accountModels); // Долгий клик на View счета. Показывате диалоговое окно
        void onClickDeleteAccount(AccountModels accountModels); // Клик на иконку удаления счета у View
    }
}
