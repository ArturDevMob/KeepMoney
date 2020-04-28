package com.arturdevmob.keepmoney.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.di.AplicationContext;

import javax.inject.Inject;

import androidx.preference.PreferenceManager;

public class AppPreferencesHelper implements PreferencesHelper {
    public static final String DEFAULT_CURRENCY_TYPE = "default_currency_type";

    private SharedPreferences mPreferences;

    @Inject
    public AppPreferencesHelper(@AplicationContext Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public CurrencyType getDefaultCurrencyType() {
        String currencyTypeStr = mPreferences.getString(DEFAULT_CURRENCY_TYPE, "RUB");

        return CurrencyType.valueOf(currencyTypeStr);
    }
}
