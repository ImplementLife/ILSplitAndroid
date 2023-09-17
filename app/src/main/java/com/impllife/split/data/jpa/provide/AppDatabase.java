package com.impllife.split.data.jpa.provide;

import android.content.Context;
import androidx.room.*;
import com.impllife.split.data.jpa.convert.BigDecimalConverter;
import com.impllife.split.data.jpa.convert.BudgetPeriodConverter;
import com.impllife.split.data.jpa.convert.DateConverter;
import com.impllife.split.data.jpa.convert.StringArrayConverter;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.entity.*;

@Database(
    entities = {
        Transaction.class,
        People.class,
        Account.class,
        NotificationInfo.class,
        NotifyAppInfo.class,
        Budget.class,
    },
    autoMigrations = {
        @AutoMigration(from = 1, to = 2),
        @AutoMigration(from = 2, to = 3),
        @AutoMigration(from = 3, to = 4),
        @AutoMigration(from = 4, to = 5),
        @AutoMigration(from = 5, to = 6),
        @AutoMigration(from = 6, to = 7),
        @AutoMigration(from = 7, to = 8),
    },
    version = 8
)
@TypeConverters({
    DateConverter.class,
    StringArrayConverter.class,
    BudgetPeriodConverter.class,
    BigDecimalConverter.class,
})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "il_split";
    private static AppDatabase instance;
    public static AppDatabase init(Context context) {
        if (instance == null || !instance.isOpen()) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
        }
        return instance;
    }

    public abstract TransactionDao getTransactionDao();
    public abstract PeopleDao getPeopleDao();
    public abstract AccountDao getAccountDao();
    public abstract NotifyInfoDao getNotifyInfoDao();
    public abstract NotifyAppInfoDao getNotifyAppInfoDao();
    public abstract BudgetDao getBudgetDao();
}