package com.arturdevmob.keepmoney.data.database.models;

import com.arturdevmob.keepmoney.data.database.DbScheme;
import com.arturdevmob.keepmoney.data.database.converters.CurrencyTypeConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = DbScheme.Account.TABLE_NAME)
@TypeConverters({CurrencyTypeConverter.class})
public class AccountModels {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    @ColumnInfo(name = DbScheme.Account.Cool.OPENING_BALANCE)
    private double openingBalance;

    @Ignore
    private double currentBalance;

    @ColumnInfo(name = DbScheme.Account.Cool.CREATE_DATE)
    private long createDate;

    @ColumnInfo(name = DbScheme.Account.Cool.CURRENCY_TYPE)
    private CurrencyType currencyType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}