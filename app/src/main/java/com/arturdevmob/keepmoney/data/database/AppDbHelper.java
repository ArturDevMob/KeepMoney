package com.arturdevmob.keepmoney.data.database;

import android.content.Context;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.data.database.models.RateCurrencyPairModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionType;
import com.arturdevmob.keepmoney.di.AplicationContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.room.Room;

public class AppDbHelper implements DbHelper {
    private AbstractDatabase mDatabase;
    private Context mContext;
    @Inject
    public AppDbHelper(@AplicationContext Context context) {
        mDatabase = Room
                .databaseBuilder(context, AbstractDatabase.class, DbScheme.DB_NAME)
                .allowMainThreadQueries()
                .build();

        mContext = context;
    }

    //////////////////////////////// ACCOUNT ///////////////////////////////////////////
    @Override
    public List<AccountModels> getAllAccounts() {
        List<AccountModels> accountList = mDatabase.accountDao().getAllAccounts();
        List<TransactionModels> transactionList = mDatabase.transactionDao().getAllTransaction();

        for (AccountModels account : accountList) {
            double accountBalance = 0;

            for (TransactionModels transaction : transactionList) {
                if (account.getId() == transaction.getAccountId()) {
                    if (transaction.getTransactionType() == TransactionType.INCOME) {
                        accountBalance = accountBalance + transaction.getAmount();
                    } else if (transaction.getTransactionType() == TransactionType.EXPENSE) {
                        accountBalance = accountBalance - transaction.getAmount();
                    }
                }
            }

            account.setCurrentBalance(account.getOpeningBalance() + accountBalance);
        }

        return accountList;
    }

    @Override
    public AccountModels getAccountById(long id) {
        AccountModels account = mDatabase.accountDao().getAccountById(id);
        List<TransactionModels> transactionList = mDatabase.transactionDao().getAllTransaction();

        double accountBalance = 0;
        for (TransactionModels transaction : transactionList) {
            if (account.getId() == transaction.getAccountId()) {
                if (transaction.getTransactionType() == TransactionType.INCOME) {
                    accountBalance = accountBalance + transaction.getAmount();
                } else if (transaction.getTransactionType() == TransactionType.EXPENSE) {
                    accountBalance = accountBalance - transaction.getAmount();
                }
            }
        }
        account.setCurrentBalance(account.getOpeningBalance() + accountBalance);

        return account;
    }

    @Override
    public void insertAccount(AccountModels accountModels) {
        mDatabase.accountDao().insert(accountModels);
    }

    @Override
    public void updateAccount(AccountModels accountModels) {
        mDatabase.accountDao().update(accountModels);
    }

    @Override
    public void deleteAccount(AccountModels accountModels) {
        List<TransactionModels> transactionList = getAllTransactionOfAccountById(accountModels.getId());

        mDatabase.transactionDao().delete(transactionList);
        mDatabase.accountDao().delete(accountModels);
    }


    //////////////////////////////// TRANSACTION ///////////////////////////////////////////

    @Override
    public List<TransactionModels> getAllTransaction() {
        List<TransactionModels> transactionList = mDatabase.transactionDao().getAllTransaction();
        List<AccountModels> accountList = mDatabase.accountDao().getAllAccounts();
        List<CategoryModels> categoryList = this.getAllCategory();

        for (TransactionModels transaction : transactionList) {
            for (AccountModels account : accountList) {
                if (transaction.getAccountId() == account.getId()) {
                    transaction.setAccountModels(account);
                }
            }

            for (CategoryModels category : categoryList) {
                if (transaction.getCategoryId() == category.getId()) {
                    transaction.setCategoryModels(category);
                }
            }
        }

        return transactionList;
    }

    @Override
    public List<TransactionModels> getAllTransactionOfAccountById(long accountId) {
        AccountModels account = mDatabase.accountDao().getAccountById(accountId);
        List<TransactionModels> transactions = mDatabase.transactionDao().getAllTransactionOfAccountById(accountId);
        List<CategoryModels> categories = this.getAllCategory();

        for (TransactionModels transactionModels : transactions) {
            transactionModels.setAccountModels(account);

            for (CategoryModels category : categories) {
                if (transactionModels.getCategoryId() == category.getId()) {
                    transactionModels.setCategoryModels(category);
                }
            }
        }

        return transactions;
    }

    @Override
    public TransactionModels getTransactionById(long transactionId) {
        TransactionModels transaction = mDatabase.transactionDao().getTransactionById(transactionId);
        AccountModels account = mDatabase.accountDao().getAccountById(transaction.getAccountId());
        CategoryModels category = mDatabase.categoryDao().getCategoryById(transaction.getCategoryId());

        transaction.setAccountModels(account);
        transaction.setCategoryModels(category);

        return transaction;
    }

    @Override
    public void insertTransaction(TransactionModels transactionModels) {
        mDatabase.transactionDao().insert(transactionModels);
    }

    @Override
    public void updateTransaction(TransactionModels transactionModels) {
        mDatabase.transactionDao().update(transactionModels);
    }

    @Override
    public void deleteTransaction(TransactionModels transactionModels) {
        mDatabase.transactionDao().delete(transactionModels);
    }

    @Override
    public void deleteTransaction(List<TransactionModels> transactionModels) {
        mDatabase.transactionDao().delete(transactionModels);
    }


    //////////////////////////////// CATEGORY ///////////////////////////////////////////


    @Override
    public List<CategoryModels> getAllCategory() {
        return mDatabase.categoryDao().getAllCategory();
    }

    @Override
    public List<CategoryModels> getAllCategoryExpense() {
        List<CategoryModels> categoryList = this.getAllCategory();
        List<CategoryModels> categoryListExpense = new ArrayList<>();
        categoryListExpense.addAll(categoryList);

        for (CategoryModels category : categoryList) {
            if (! category.isExpense()) {
                categoryListExpense.remove(category);
            }
        }

        return categoryListExpense;
    }

    @Override
    public List<CategoryModels> getAllCategoryIncome() {
        List<CategoryModels> categoryList = this.getAllCategory();
        List<CategoryModels> categoryListIncome = new ArrayList<>();
        categoryListIncome.addAll(categoryList);

        for (CategoryModels category : categoryList) {
            if (category.isExpense()) {
                categoryListIncome.remove(category);
            }
        }

        return categoryListIncome;
    }

    @Override
    public CategoryModels getCategoryById(long categoryId) {
        return mDatabase.categoryDao().getCategoryById(categoryId);
    }

    @Override
    public void insertCategory(CategoryModels category) {
        mDatabase.categoryDao().insertCategory(category);
    }

    @Override
    public void updateCategory(CategoryModels category) {
        mDatabase.categoryDao().updateCategory(category);
    }

    @Override
    public void deleteCategory(CategoryModels category) {
        mDatabase.categoryDao().deleteCategory(category);
    }


    //////////////////////////////// CATEGORY ///////////////////////////////////////////


    @Override
    public void deleteAllData() {
        mDatabase.accountDao().delete(mDatabase.accountDao().getAllAccounts());
        mDatabase.transactionDao().delete(mDatabase.transactionDao().getAllTransaction());
        mDatabase.categoryDao().deleteCategory(mDatabase.categoryDao().getAllCategory());
    }

    @Override
    public RateCurrencyPairModels getRateCurrencyPairFromDb(String currencyPair) {
        return mDatabase.rateCurrencyPairDao().getRateCurrencyPair(currencyPair);
    }

    @Override
    public void addRateCurrencyPairFromDb(RateCurrencyPairModels rateCurrencyPairModels) {
        mDatabase.rateCurrencyPairDao().addRateCurrencyPair(rateCurrencyPairModels);
    }
}
