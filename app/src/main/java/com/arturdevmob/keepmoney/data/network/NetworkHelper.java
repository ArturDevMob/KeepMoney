package com.arturdevmob.keepmoney.data.network;

import java.io.IOException;

public interface NetworkHelper {
    double getRatePairCurrencyFromNetwork(String firstCurrency, String secondCurrency) throws IOException;
}
