package com.arturdevmob.keepmoney.data.database.converters;

import com.arturdevmob.keepmoney.data.database.models.CurrencyType;

import androidx.room.TypeConverter;

public class CurrencyTypeConverter {
    @TypeConverter
    public CurrencyType toCurrencyType(String str) {
        return CurrencyType.valueOf(str);
    }

    @TypeConverter
    public String fromCurrencyType(CurrencyType currencyType) {
        return currencyType.toString();
    }
}
