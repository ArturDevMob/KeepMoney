package com.arturdevmob.keepmoney.ui.base;

import android.content.Context;

import com.arturdevmob.keepmoney.di.component.ActivityComponent;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements BaseMvpView {
    private BaseActivity mBaseActivity;

    public abstract void setupToolbar();

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnackBar(int resId) {
        Snackbar.make(getView(), getResources().getString(resId), Snackbar.LENGTH_LONG).show();
    }

    public ActivityComponent getActivityComponent() {
        return mBaseActivity.getActivityComponent();
    }

    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            this.mBaseActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mBaseActivity = null;
    }
}
