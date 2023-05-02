package com.impllife.split.data.jpa.provide;

import android.content.Context;
import androidx.room.*;
import com.impllife.split.data.jpa.convert.DateConverter;
import com.impllife.split.data.jpa.entity.*;
import com.impllife.split.data.jpa.entity.Transaction;

@Database(
    entities = {
        Transaction.class,
        People.class,
        Account.class,
        NotificationInfo.class,
        NotifyAppInfo.class
    },
    autoMigrations = {
        @AutoMigration(from = 1, to = 2),
        @AutoMigration(from = 2, to = 3),
    },
    version = 3
)
@TypeConverters(
    DateConverter.class
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "il_split";
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
}