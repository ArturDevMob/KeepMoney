package com.arturdevmob.keepmoney.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.ui.base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutFragment extends BaseFragment implements AboutMvpView {
    public static final String TAG = "AboutFragment";

    @Inject AboutMvpPresenter<AboutMvpView> mPresenter;

    @BindView(R.id.include_toolbar)
    Toolbar mToolbar;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(AboutFragment.this);

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        mPresenter.attachView(AboutFragment.this);

        ButterKnife.bind(this, view);

        setupToolbar();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mPresenter.detachView();
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
    }
}
