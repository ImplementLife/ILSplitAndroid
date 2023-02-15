package com.impllife.split.data.jpa.provide;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DaoFactory {
    private final TransactionDao transactionDao;
    private final PeopleDao peopleDao;

    public DaoFactory() {
        RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(MainActivity.getInstance(), AppDatabase.class, "il-split-db");
        AppDatabase.getMigrations().forEach(builder::addMigrations);

        AppDatabase db = builder.build();

        transactionDao = db.getTransactionDao();
        peopleDao = db.getPeopleDao();
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }
    public PeopleDao getPeopleDao() {
        return peopleDao;
    }
}
