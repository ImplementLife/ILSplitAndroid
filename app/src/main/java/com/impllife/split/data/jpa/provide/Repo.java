package com.impllife.split.data.jpa.provide;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.impllife.split.data.jpa.entity.Rec;
import com.impllife.split.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Repo {

    private DBHelper dbHelper = new DBHelper(MainActivity.instance);

    public void exe(Consumer<SQLiteDatabase> consumer) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        consumer.accept(database);
        dbHelper.close();
    }

    public void add(Rec rec) {
        exe(database -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_NAME, rec.getName());
            contentValues.put(DBHelper.KEY_MAIL, rec.getEmail());

            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
        });
    }

    public List<Rec> readAll() {
        List<Rec> result = new ArrayList<>();
        exe(database -> {
            Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                do {
                    Rec rec = new Rec();
                    rec.setId(cursor.getInt(idIndex));
                    rec.setEmail(cursor.getString(emailIndex));
                    rec.setName(cursor.getString(nameIndex));
                    result.add(rec);
                } while (cursor.moveToNext());
            } else {
                Log.d("mLog","0 rows");
            }
            cursor.close();
        });
        return result;
    }

    public void deleteAll() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_CONTACTS, null, null);
    }

    public boolean deleteById(int id) {
        AtomicInteger res = new AtomicInteger();
        exe(db -> {
            res.set(db.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + " = " + id, null));
        });
        return res.get() > 0;
    }
}
