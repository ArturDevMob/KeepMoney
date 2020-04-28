package com.arturdevmob.keepmoney.data.database.models;

import com.arturdevmob.keepmoney.data.database.DbScheme;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = DbScheme.CurrencyPairRate.TABLE_NAME)
public class RateCurrencyPairModels {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DbScheme.CurrencyPairRate.Cool.PAIR_CURRENCY)
    private String pairCurrency;

    private double rate;

    @ColumnInfo(name = DbScheme.CurrencyPairRate.Cool.CREATE_DATE)
    private long createDate;

    public String getPairCurrency() {
        return pairCurrency;
    }

    public void setPairCurrency(String pairCurrency) {
        this.pairCurrency = pairCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
