package com.arturdevmob.keepmoney.data;

import com.arturdevmob.keepmoney.data.database.DbHelper;
import com.arturdevmob.keepmoney.data.local.LocalHelper;
import com.arturdevmob.keepmoney.data.network.NetworkHelper;
import com.arturdevmob.keepmoney.data.preferences.PreferencesHelper;

import java.io.IOException;

public interface DataManager extends DbHelper, PreferencesHelper, NetworkHelper, LocalHelper {
    double convertAmountToDefaultCurrencyType() throws IOException;
}
