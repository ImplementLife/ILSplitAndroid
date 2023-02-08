package com.impllife.split.data.jpa.provide;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.impllife.split.data.jpa.entity.Transaction;

@Database(
    entities = {
        Transaction.class
        /*, AnotherEntityType.class, AThirdEntityType.class */
    },
    version = 2
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao getTransactionDao();
}