package com.arturdevmob.keepmoney.data.database;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.data.database.models.RateCurrencyPairModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;

import java.util.List;

public interface DbHelper {
    List<AccountModels> getAllAccounts();
    AccountModels getAccountById(long id);
    void insertAccount(AccountModels accountModels);
    void updateAccount(AccountModels accountModels);
    void deleteAccount(AccountModels accountModels);

    List<TransactionModels> getAllTransaction();
    List<TransactionModels> getAllTransactionOfAccountById(long accountId);
    TransactionModels getTransactionById(long transactionId);
    void insertTransaction(TransactionModels transactionModels);
    void updateTransaction(TransactionModels transactionModels);
    void deleteTransaction(TransactionModels transactionModels);
    void deleteTransaction(List<TransactionModels> transactionModels);

    List<CategoryModels> getAllCategory();
    List<CategoryModels> getAllCategoryExpense();
    List<CategoryModels> getAllCategoryIncome();
    CategoryModels getCategoryById(long categoryId);
    void insertCategory(CategoryModels category);
    void updateCategory(CategoryModels category);
    void deleteCategory(CategoryModels category);

    void deleteAllData();

    RateCurrencyPairModels getRateCurrencyPairFromDb(String currencyPair);
    void addRateCurrencyPairFromDb(RateCurrencyPairModels rateCurrencyPairModels);
}
