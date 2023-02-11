package com.impllife.split.data.jpa.provide;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.impllife.split.data.jpa.convert.DateConverter;
import com.impllife.split.data.jpa.entity.Transaction;

@Database(
    entities = {
        Transaction.class
        /*, AnotherEntityType.class, AThirdEntityType.class */
    },
    version = 1
)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao getTransactionDao();
}