package com.arturdevmob.keepmoney.data.preferences;

import com.arturdevmob.keepmoney.data.database.models.CurrencyType;

public interface PreferencesHelper {
    CurrencyType getDefaultCurrencyType();
}
