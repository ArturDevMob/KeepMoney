package com.arturdevmob.keepmoney.ui.accounts.detail.transaction;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.Utils;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private Context mContext;
    private OnEventAdapter mOnEventAdapter;
    private List<TransactionModels> mTransactionModelsList;

    public interface OnEventAdapter {
        void onClickDeleteTransactionButton(TransactionModels transactionModels);
        void onClickEditTransactionButton(TransactionModels transactionModels);
    }

    @Inject
    public TransactionAdapter(Context context) {
        mContext = context;
        mTransactionModelsList = new ArrayList<>();
    }

    public void setCallback(OnEventAdapter callback) {
        mOnEventAdapter = callback;
    }

    public void setData(List<TransactionModels> transactionModelsList) {
        mTransactionModelsList.clear();
        mTransactionModelsList.addAll(transactionModelsList);
    }

    public List<TransactionModels> getData() {
        return mTransactionModelsList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);

        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Resources resources = mContext.getResources();

        TransactionModels transactionModels = mTransactionModelsList.get(position);
        CategoryModels categoryModels = transactionModels.getCategoryModels();

        holder.mTitleTextView.setText(transactionModels.getTitle());
        holder.mDescriptionTextView.setText(transactionModels.getDescription());
        holder.mCreateDateTextView.setText(Utils.dateLongFormatForString(transactionModels.getCreateDate()));
        holder.mAmountTextView.setText(Utils.amountFormatNoCurrencyType(transactionModels.getAmount()));
        holder.mFullInfoTransaction.setVisibility(View.GONE);
        holder.mIconCategoryImageView.setImageDrawable(resources.getDrawable(R.drawable.no_image_category));

        if (categoryModels != null && ! categoryModels.getImagePath().isEmpty()) {
            Picasso.get()
                    .load(transactionModels.getCategoryModels().getImagePath())
                    .into(holder.mIconCategoryImageView);
        }

        if (transactionModels.getTransactionType() == TransactionType.INCOME) {
            holder.mTransactionColorLabelFrameLayout.setBackgroundResource(R.color.brilliant_green);
        } else if (transactionModels.getTransactionType() == TransactionType.EXPENSE) {
            holder.mTransactionColorLabelFrameLayout.setBackgroundResource(R.color.deep_yellow_pink);
        }

        if (categoryModels == null) {
            holder.mCategoryImageView.setImageDrawable(null);
            holder.mCategoryTextView.setText(null);
        } else {
            holder.mCategoryImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_category));
            holder.mCategoryTextView.setText(transactionModels.getCategoryModels().getTitle());
        }

        if (transactionModels.getDescription().isEmpty()) {
            holder.mDescriptionImageView.setImageDrawable(null);
            holder.mDescriptionTextView.setText(null);
        } else {
            holder.mDescriptionImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_description));
            holder.mDescriptionTextView.setText(transactionModels.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return mTransactionModelsList.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_full_info) View mFullInfoTransaction;
        @BindView(R.id.icon_category_image_view) ImageView mIconCategoryImageView;
        @BindView(R.id.title_text_view) TextView mTitleTextView;
        @BindView(R.id.category_image_view) ImageView mCategoryImageView;
        @BindView(R.id.category_text_view) TextView mCategoryTextView;
        @BindView(R.id.description_image_view) ImageView mDescriptionImageView;
        @BindView(R.id.description_text_view) TextView mDescriptionTextView;
        @BindView(R.id.create_date_text_view) TextView mCreateDateTextView;
        @BindView(R.id.amount_text_view) TextView mAmountTextView;
        @BindView(R.id.transaction_type_color_label) FrameLayout mTransactionColorLabelFrameLayout;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_view_transaction)
        public void onClickTransactionCardView() {
            if (mFullInfoTransaction.getVisibility() == View.GONE) {
                mFullInfoTransaction.setVisibility(View.VISIBLE);
            } else {
                mFullInfoTransaction.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.edit_button)
        public void onClickEditButton() {
            mOnEventAdapter.onClickEditTransactionButton(mTransactionModelsList.get(getAdapterPosition()));
        }

        @OnClick(R.id.delete_button)
        public void onClickDeleteButton() {
            mOnEventAdapter.onClickDeleteTransactionButton(mTransactionModelsList.get(getAdapterPosition()));
        }
    }
}
