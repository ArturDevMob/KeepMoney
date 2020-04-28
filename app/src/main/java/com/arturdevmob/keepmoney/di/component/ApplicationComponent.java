package com.arturdevmob.keepmoney.di.component;

import android.content.Context;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.di.AplicationContext;
import com.arturdevmob.keepmoney.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    @AplicationContext
    Context context();

    DataManager dataManager();
}
