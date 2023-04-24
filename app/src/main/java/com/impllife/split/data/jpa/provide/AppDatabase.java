package com.impllife.split.data.jpa.provide;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.impllife.split.data.jpa.convert.DateConverter;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;

@Database(
    entities = {
        Transaction.class,
        People.class,
        Account.class,
        NotificationInfo.class
    },
    version = 1
)
@TypeConverters(
    DateConverter.class
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "il_split";
    public static AppDatabase init(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public abstract TransactionDao getTransactionDao();
    public abstract PeopleDao getPeopleDao();
    public abstract AccountDao getAccountDao();
    public abstract NotificationDao getNotificationDao();
}