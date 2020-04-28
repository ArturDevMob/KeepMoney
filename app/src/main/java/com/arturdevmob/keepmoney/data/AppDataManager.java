package com.arturdevmob.keepmoney.data;

import com.arturdevmob.keepmoney.data.database.DbHelper;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.data.database.models.RateCurrencyPairModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.data.local.LocalHelper;
import com.arturdevmob.keepmoney.data.network.NetworkHelper;
import com.arturdevmob.keepmoney.data.preferences.PreferencesHelper;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class AppDataManager implements DataManager {
    private DbHelper mDbHelper;
    private PreferencesHelper mPreferencesHelper;
    private NetworkHelper mNetworkHelper;
    private LocalHelper mLocalHelper;

    @Inject
    public AppDataManager(DbHelper dbHelper, PreferencesHelper preferencesHelper, NetworkHelper networkHelper, LocalHelper localHelper) {
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mNetworkHelper = networkHelper;
        mLocalHelper = localHelper;
    }

    @Override
    public List<AccountModels> getAllAccounts() {
        return mDbHelper.getAllAccounts();
    }

    @Override
    public AccountModels getAccountById(long id) {
        return mDbHelper.getAccountById(id);
    }

    @Override
    public void insertAccount(AccountModels accountModels) {
        mDbHelper.insertAccount(accountModels);
    }

    @Override
    public void updateAccount(AccountModels accountModels) {
        mDbHelper.updateAccount(accountModels);
    }

    @Override
    public void deleteAccount(AccountModels accountModels) {
        mDbHelper.deleteAccount(accountModels);
    }

    @Override
    public List<TransactionModels> getAllTransaction() {
        return mDbHelper.getAllTransaction();
    }

    @Override
    public List<TransactionModels> getAllTransactionOfAccountById(long accountId) {
        return mDbHelper.getAllTransactionOfAccountById(accountId);
    }

    @Override
    public TransactionModels getTransactionById(long transactionId) {
        return mDbHelper.getTransactionById(transactionId);
    }

    @Override
    public void insertTransaction(TransactionModels transactionModels) {
        mDbHelper.insertTransaction(transactionModels);
    }

    @Override
    public void updateTransaction(TransactionModels transactionModels) {
        mDbHelper.updateTransaction(transactionModels);
    }

    @Override
    public void deleteTransaction(TransactionModels transactionModels) {
        mDbHelper.deleteTransaction(transactionModels);
    }

    @Override
    public void deleteTransaction(List<TransactionModels> transactionModels) {
        mDbHelper.deleteTransaction(transactionModels);
    }

    @Override
    public List<CategoryModels> getAllCategory() {
        return mDbHelper.getAllCategory();
    }

    @Override
    public List<CategoryModels> getAllCategoryExpense() {
        return mDbHelper.getAllCategoryExpense();
    }

    @Override
    public List<CategoryModels> getAllCategoryIncome() {
        return mDbHelper.getAllCategoryIncome();
    }

    @Override
    public CategoryModels getCategoryById(long categoryId) {
        return mDbHelper.getCategoryById(categoryId);
    }

    @Override
    public void insertCategory(CategoryModels category) {
        mDbHelper.insertCategory(category);
    }

    @Override
    public void updateCategory(CategoryModels category) {
        mDbHelper.updateCategory(category);
    }

    @Override
    public void deleteCategory(CategoryModels category) {
        mDbHelper.deleteCategory(category);
    }

    @Override
    public void deleteAllData() {
        mDbHelper.deleteAllData();
    }

    @Override
    public CurrencyType getDefaultCurrencyType() {
        return mPreferencesHelper.getDefaultCurrencyType();
    }

    @Override
    public double convertAmountToDefaultCurrencyType() throws IOException {
        double generalBalance = 0;
        double accountBalance = 0;

        CurrencyType defaultCurrencyType = mPreferencesHelper.getDefaultCurrencyType();
        List<AccountModels> accountList = mDbHelper.getAllAccounts();

        for (AccountModels account : accountList) {
            if (account.getCurrencyType() == defaultCurrencyType) {
                accountBalance = account.getCurrentBalance();
            } else {
                String firstCurrency = account.getCurrencyType().name();
                String secondCurrency = defaultCurrencyType.name();
                double rate;

                // Получаем курс пары валют из бд, если курс валюты есть в бд и он не устарел
                RateCurrencyPairModels rateCurrencyPairModels = mDbHelper.getRateCurrencyPairFromDb(firstCurrency + secondCurrency);
                if (rateCurrencyPairModels != null && rateCurrencyPairModels.getCreateDate() + (12 * 60 * 60 * 1000) > System.currentTimeMillis()) {
                    rate = rateCurrencyPairModels.getRate();
                } else {
                    try {
                        // Получаем курс из сети. Если сети нет, вылетает исключение
                        rate = this.getRatePairCurrencyFromNetwork(firstCurrency, secondCurrency);

                        rateCurrencyPairModels = new RateCurrencyPairModels();
                        rateCurrencyPairModels.setPairCurrency(firstCurrency + secondCurrency);
                        rateCurrencyPairModels.setRate(rate);
                        rateCurrencyPairModels.setCreateDate(System.currentTimeMillis());
                        this.addRateCurrencyPairFromDb(rateCurrencyPairModels);
                    } catch (IOException ex) {
                        throw new IOException();
                    }
                }

                accountBalance = account.getCurrentBalance() * rate;
            }

            generalBalance = generalBalance + accountBalance;
            accountBalance = 0;
        }

        return generalBalance;
    }

    @Override
    public double getRatePairCurrencyFromNetwork(String firstCurrency, String secondCurrency) throws IOException {
        return mNetworkHelper.getRatePairCurrencyFromNetwork(firstCurrency, secondCurrency);
    }

    @Override
    public RateCurrencyPairModels getRateCurrencyPairFromDb(String currencyPair) {
        return mDbHelper.getRateCurrencyPairFromDb(currencyPair);
    }

    @Override
    public void addRateCurrencyPairFromDb(RateCurrencyPairModels rateCurrencyPairModels) {
        mDbHelper.addRateCurrencyPairFromDb(rateCurrencyPairModels);
    }

    @Override
    public List<String> getPathsToImagesCategory() {
        return mLocalHelper.getPathsToImagesCategory();
    }
}
