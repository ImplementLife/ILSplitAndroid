package com.impllife.split.data.jpa.provide;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.impllife.split.ui.MainActivity;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import javax.persistence.Entity;
import java.sql.SQLException;
import java.util.List;

import static com.impllife.split.service.Util.getAnnotatedClasses;

public class DBProvider extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "il-split-db";
    private static final int DATABASE_VERSION = 1;

    private List<Class<?>> entities = getAnnotatedClasses(Entity.class);
    private ConnectionSource connectionSource;

    public void save(Object entity) {
        try {
            Class<Object> aClass = (Class<Object>) entity.getClass();
            if (!entities.contains(aClass)) throw new IllegalArgumentException();
            Dao<Object, Long> dao = DaoManager.createDao(connectionSource, aClass);
            dao.createOrUpdate(entity);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public DBProvider() {
        super(MainActivity.getInstance(), DATABASE_NAME, null, DATABASE_VERSION);
        connectionSource = new AndroidConnectionSource(this);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.i("DatabaseHelper", "onCreate start");
        for (Class<?> aClass : entities) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, aClass);
            } catch (SQLException e) {
                Log.e(aClass.getName(), e.getMessage());
            }
        }
        Log.i("DatabaseHelper", "onCreate end");
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i("DatabaseHelper", "onUpgrade");
        for (Class<?> entity : entities) {

        }
        Log.i("DatabaseHelper", "onUpgrade end");
    }
    @Override
    public void close() {
            Log.i("DatabaseHelper", "close");
            super.close();
        }
}
