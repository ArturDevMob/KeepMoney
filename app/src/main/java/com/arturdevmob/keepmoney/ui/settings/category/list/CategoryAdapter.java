package com.arturdevmob.keepmoney.ui.settings.category.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private OnEventAdapterForEditView mEventAdapterForEditView;
    private List<CategoryModels> mCategoryModels;
    private CategoryModels mSelectedCategory;

    @Inject
    public CategoryAdapter(Context context) {
        mContext = context;
        mCategoryModels = new ArrayList<>();
    }

    // Интерфейс для общения с вьюхой которая редактирует список категорий
    public interface OnEventAdapterForEditView {
        void onDeleteCategory(CategoryModels categoryModels);
        void onEditCategory(CategoryModels categoryModels);
    }

    public void setCallback(Object callback) {
        if (callback instanceof OnEventAdapterForEditView) {
            mEventAdapterForEditView = (OnEventAdapterForEditView) callback;
        }
    }

    public void setData(List<CategoryModels> categoryModelsList) {
        mCategoryModels.clear();
        mCategoryModels.addAll(categoryModelsList);

        notifyDataSetChanged();
    }

    public void clearData() {
        mCategoryModels.clear();
    }

    public List<CategoryModels> getData() {
        return mCategoryModels;
    }

    public void setSelectedCategory(CategoryModels category) {
        mSelectedCategory = category;
        notifyDataSetChanged();
    }

    public CategoryModels getSelectedCategory() {
        return mSelectedCategory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModels categoryModels = mCategoryModels.get(position);

        if (categoryModels.getImagePath().isEmpty()) {
            holder.mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_image_category));
        } else {
            Picasso.get()
                    .load(categoryModels.getImagePath())
                    .into(holder.mImageView);
        }

        holder.mTitleTextView.setText(categoryModels.getTitle());

        if (mEventAdapterForEditView == null) {
            holder.mDeleteImageView.setVisibility(View.GONE);
            holder.mSortImageView.setVisibility(View.GONE);

            if (mSelectedCategory != null && categoryModels.getId() == mSelectedCategory.getId()) {
                holder.mApplyImageView.setVisibility(View.VISIBLE);
            } else {
                holder.mApplyImageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCategoryModels.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view) ImageView mImageView;
        @BindView(R.id.title_text_view) TextView mTitleTextView;
        @BindView(R.id.apply_image_view) ImageView mApplyImageView;
        @BindView(R.id.delete_image_view) ImageView mDeleteImageView;
        @BindView(R.id.sort_image_view) ImageView mSortImageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.delete_image_view)
        public void onClickDeleteImageView() {
            mEventAdapterForEditView.onDeleteCategory(mCategoryModels.get(getLayoutPosition()));
        }

        @OnClick(R.id.title_text_view)
        public void onCLickTitleTextView() {
            CategoryModels categoryModels = mCategoryModels.get(getLayoutPosition());
            if (mEventAdapterForEditView == null) {
                // Это режим выбора категории для транзакции, значит устанавливаем категорию для транз.
                setSelectedCategory(categoryModels);
            } else {
                // Это режим редактирования категорий, значит открываем активити для редактирования категории
                mEventAdapterForEditView.onEditCategory(categoryModels);
            }
        }
    }
}
