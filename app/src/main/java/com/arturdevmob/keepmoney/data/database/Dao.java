package com.arturdevmob.keepmoney.data.database;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.data.database.models.RateCurrencyPairModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

public interface Dao {
    @androidx.room.Dao
    interface Account {
        @Query("SELECT * FROM " + DbScheme.Account.TABLE_NAME)
        List<AccountModels> getAllAccounts();

        @Query("SELECT * FROM " + DbScheme.Account.TABLE_NAME + " WHERE " + DbScheme.Account.Cool.ID + " = :id")
        AccountModels getAccountById(long id);

        @Insert
        void insert(AccountModels accountModels);

        @Update
        void update(AccountModels accountModels);

        @Delete
        void delete(AccountModels accountModels);

        @Delete
        void delete(List<AccountModels> accountModelsList);
    }

    @androidx.room.Dao
    interface Transaction {
        @Query("SELECT * FROM " + DbScheme.Transaction.TABLE_NAME + " ORDER BY create_date DESC")
        List<TransactionModels> getAllTransaction();

        @Query("SELECT * FROM " + DbScheme.Transaction.TABLE_NAME + " WHERE " + DbScheme.Transaction.Cool.ACCOUNT_ID + " = :accountId ORDER BY create_date DESC")
        List<TransactionModels> getAllTransactionOfAccountById(long accountId);

        @Query("SELECT * FROM " + DbScheme.Transaction.TABLE_NAME + " WHERE " + DbScheme.Transaction.Cool.ID + " = :transactionId")
        TransactionModels getTransactionById(long transactionId);

        @Insert
        void insert(TransactionModels transactionModels);

        @Update
        void update(TransactionModels transactionModels);

        @Delete
        void delete(TransactionModels transactionModels);

        @Delete
        void delete(List<TransactionModels> transactionModels);
    }

    @androidx.room.Dao
    interface Category {
        @Query("SELECT * FROM " + DbScheme.Category.TABLE_NAME)
        List<CategoryModels> getAllCategory();

        @Query("SELECT * FROM " + DbScheme.Category.TABLE_NAME + " WHERE " + DbScheme.Category.Cool.ID + " = :id")
        CategoryModels getCategoryById(long id);

        @Insert
        void insertCategory(CategoryModels category);

        @Update
        void updateCategory(CategoryModels category);

        @Delete
        void deleteCategory(CategoryModels category);

        @Delete
        void deleteCategory(List<CategoryModels> categoryModelsList);
    }

    @androidx.room.Dao
    interface RateCurrencyPair {
        @Query("SELECT * FROM " + DbScheme.CurrencyPairRate.TABLE_NAME + " WHERE " + DbScheme.CurrencyPairRate.Cool.PAIR_CURRENCY + " = :pairCurrency")
        RateCurrencyPairModels getRateCurrencyPair(String pairCurrency);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void addRateCurrencyPair(RateCurrencyPairModels rateCurrencyPairModels);
    }
}
