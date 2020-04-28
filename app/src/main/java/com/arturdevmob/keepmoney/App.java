package com.arturdevmob.keepmoney;

import android.app.Application;

import com.arturdevmob.keepmoney.di.component.ApplicationComponent;
import com.arturdevmob.keepmoney.di.component.DaggerApplicationComponent;
import com.arturdevmob.keepmoney.di.module.ApplicationModule;

public class App extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}