package com.arturdevmob.keepmoney.di.module;

import android.content.Context;

import com.arturdevmob.keepmoney.data.AppDataManager;
import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.AppDbHelper;
import com.arturdevmob.keepmoney.data.database.DbHelper;
import com.arturdevmob.keepmoney.data.local.AppLocalHelper;
import com.arturdevmob.keepmoney.data.local.LocalHelper;
import com.arturdevmob.keepmoney.data.network.AppNetworkHelper;
import com.arturdevmob.keepmoney.data.network.NetworkHelper;
import com.arturdevmob.keepmoney.data.preferences.AppPreferencesHelper;
import com.arturdevmob.keepmoney.data.preferences.PreferencesHelper;
import com.arturdevmob.keepmoney.di.AplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Context mAppContext;

    public ApplicationModule(Context appContext) {
        mAppContext = appContext;
    }

    @AplicationContext
    @Provides
    Context provideContext() {
        return mAppContext;
    }

    @Singleton
    @Provides
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Singleton
    @Provides
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Singleton
    @Provides
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Singleton
    @Provides
    NetworkHelper provideNetworkHelper(AppNetworkHelper appNetworkHelper) {
        return appNetworkHelper;
    }

    @Singleton
    @Provides
    LocalHelper provideLocalHelper(AppLocalHelper appLocalHelper) {
        return appLocalHelper;
    }
}
