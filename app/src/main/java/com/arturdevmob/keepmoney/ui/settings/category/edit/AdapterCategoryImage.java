package com.arturdevmob.keepmoney.ui.settings.category.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arturdevmob.keepmoney.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AdapterCategoryImage extends RecyclerView.Adapter<AdapterCategoryImage.CategoryImageViewHolder> {
    private Context mContext;
    private List<String> mPathsImages;
    private List<Drawable> mImages;
    private int mPositionOfSelectedCategoryInList;

    @Inject
    public AdapterCategoryImage(Context context) {
        mContext = context;
        mPathsImages = new ArrayList<>();
        mImages = new ArrayList<>();
        mPositionOfSelectedCategoryInList = -1;
    }

    public void setData(List<String> pathsImages) {
        mPathsImages.clear();
        mPathsImages.addAll(pathsImages);

        this.notifyDataSetChanged();
    }

    public String getPathSelectedImage() {
        if (mPositionOfSelectedCategoryInList >= 0) {
            return mPathsImages.get(mPositionOfSelectedCategoryInList);
        }

        return null;
    }

    public void setSelectedImage(String pathSelectedImage) {
        for (int i = 0; i < mPathsImages.size(); i++) {
            if (mPathsImages.get(i).equals(pathSelectedImage)) {
                mPositionOfSelectedCategoryInList = i;
            }
        }

        notifyDataSetChanged();
    }

    public Drawable getSelectedImage() {
        if (mPositionOfSelectedCategoryInList >= 0) {
            return mImages.get(mPositionOfSelectedCategoryInList);
        }

        return null;
    }

    @NonNull
    @Override
    public CategoryImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_image, parent, false);

        return new CategoryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryImageViewHolder holder, int position) {
        Observable.fromCallable(() -> Picasso.get().load(mPathsImages.get(position)).get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);

                        if (position == mPositionOfSelectedCategoryInList) {
                            drawable.setColorFilter(mContext.getResources()
                                    .getColor(R.color.brilliant_green_50_alpha), PorterDuff.Mode.SRC_ATOP);
                        } else {
                            drawable.clearColorFilter();
                        }

                        holder.mImageView.setBackground(drawable);
                        mImages.add(drawable);
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onComplete() { }
                });
    }

    @Override
    public int getItemCount() {
        return mPathsImages.size();
    }

    class CategoryImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view) ImageView mImageView;

        public CategoryImageViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.image_view)
        public void onClickImageView() {
            int positionOfSelectedCategoryInList = mPositionOfSelectedCategoryInList;

            if (mPositionOfSelectedCategoryInList == getAdapterPosition()) {
                mPositionOfSelectedCategoryInList = -1;
            } else {
                mPositionOfSelectedCategoryInList = getAdapterPosition();
            }

            if (positionOfSelectedCategoryInList >= 0) {
                notifyItemChanged(positionOfSelectedCategoryInList);
            }
            notifyItemChanged(getAdapterPosition());
        }
    }
}
