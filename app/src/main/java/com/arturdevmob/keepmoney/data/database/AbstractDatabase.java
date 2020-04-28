package com.arturdevmob.keepmoney.data.database;

import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.RateCurrencyPairModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AccountModels.class, TransactionModels.class, CategoryModels.class, RateCurrencyPairModels.class}, version = DbScheme.DB_VERSION)
public abstract class AbstractDatabase extends RoomDatabase {
    public abstract Dao.Account accountDao();
    public abstract Dao.Transaction transactionDao();
    public abstract Dao.Category categoryDao();
    public abstract Dao.RateCurrencyPair rateCurrencyPairDao();
}
