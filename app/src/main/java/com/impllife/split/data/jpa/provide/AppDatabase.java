package com.impllife.split.data.jpa.provide;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import com.impllife.split.data.jpa.convert.DateConverter;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.LinkedList;
import java.util.List;

@Database(
    entities = {
        Transaction.class,
        People.class,
        Account.class
    },
    version = 2
)
@TypeConverters(
    DateConverter.class
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao getTransactionDao();
    public abstract PeopleDao getPeopleDao();
    public abstract AccountDao getAccountDao();

    public static List<Migration> getMigrations() {
        return new LinkedList<>();
    }
}