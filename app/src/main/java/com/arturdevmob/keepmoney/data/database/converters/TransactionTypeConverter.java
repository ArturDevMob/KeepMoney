package com.arturdevmob.keepmoney.data.database.converters;

import com.arturdevmob.keepmoney.data.database.models.TransactionType;

import androidx.room.TypeConverter;

public class TransactionTypeConverter {
    @TypeConverter
    public TransactionType toTransactionType(String str) {
        return TransactionType.valueOf(str);
    }

    @TypeConverter
    public String fromTransactionType(TransactionType transactionType) {
        return transactionType.toString();
    }
}
