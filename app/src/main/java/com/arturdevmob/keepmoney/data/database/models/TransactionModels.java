package com.arturdevmob.keepmoney.data.database.models;

import com.arturdevmob.keepmoney.data.database.DbScheme;
import com.arturdevmob.keepmoney.data.database.converters.TransactionTypeConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = DbScheme.Transaction.TABLE_NAME)
@TypeConverters({TransactionTypeConverter.class})
public class TransactionModels {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = DbScheme.Transaction.Cool.ACCOUNT_ID)
    private long accountId;

    @ColumnInfo(name = DbScheme.Transaction.Cool.CATEGORY_ID)
    private long categoryId;

    private String title;

    private String description;

    private double amount;

    @ColumnInfo(name = DbScheme.Transaction.Cool.TRANSACTION_TYPE)
    private TransactionType transactionType;

    @ColumnInfo(name = DbScheme.Transaction.Cool.CREATE_DATE)
    private long createDate;

    @Ignore
    private AccountModels accountModels;

    @Ignore
    private CategoryModels categoryModels;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public AccountModels getAccountModels() {
        return accountModels;
    }

    public void setAccountModels(AccountModels accountModels) {
        this.accountModels = accountModels;
    }

    public CategoryModels getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(CategoryModels categoryModels) {
        this.categoryModels = categoryModels;
    }
}
