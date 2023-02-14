package com.impllife.split.data.jpa.provide;

import android.util.Log;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.impllife.split.data.jpa.convert.DateConverter;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.LinkedList;
import java.util.List;

import static com.impllife.split.data.jpa.provide.AppDatabase.DB_VERSION;

@Database(
    entities = {
        Transaction.class,
        People.class
    },
    version = DB_VERSION
)
@TypeConverters(
    DateConverter.class
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao getTransactionDao();
    public abstract PeopleDao getPeopleDao();

    public static final int DB_VERSION = 1;

    public static List<Migration> getMigrations() {
        List<Migration> migrations = new LinkedList<>();
        migrations.add(new Migration(1, 2) {
            public void migrate(SupportSQLiteDatabase db) {
                Log.i("Applying migration ", "from 1 to 2");
                db.execSQL("ALTER TABLE `people` ADD COLUMN accountId INTEGER");
                db.execSQL("ALTER TABLE `people` ADD COLUMN pseudonym TEXT");
            }
        });

        return migrations;
    }
}