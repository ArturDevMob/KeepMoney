package com.arturdevmob.keepmoney.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.arturdevmob.keepmoney.App;
import com.arturdevmob.keepmoney.di.component.ActivityComponent;
import com.arturdevmob.keepmoney.di.component.DaggerActivityComponent;
import com.arturdevmob.keepmoney.di.module.ActivityModule;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements BaseMvpView {
    private ActivityComponent mActivityComponent;

    public abstract void setupToolbar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .build();
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnackBar(int resId) {
        Snackbar.make(findViewById(android.R.id.content), getResources().getString(resId), Snackbar.LENGTH_LONG).show();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }
}
