package com.impllife.split.service;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.AppDatabase;
import com.impllife.split.ui.MainActivity;

import java.util.List;
import java.util.Optional;

public class DataService implements DataImage {
    //region singleton

    private static DataService instance;
    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }
    private DataService() {}
    //endregion

    private final AppDatabase db = AppDatabase.init(MainActivity.getInstance());

    public void insert(Transaction transactions) {
        db.getTransactionDao().insert(transactions);
    }
    public void insert(People people) {
        db.getPeopleDao().insert(people);
    }
    public void insert(Account account) {
        db.getAccountDao().insert(account);
    }

    public void update(People people) {
        db.getPeopleDao().update(people);
    }

    public void delete(Transaction transactions) {
        db.getTransactionDao().delete(transactions);
    }
    public void delete(People people) {
        db.getPeopleDao().delete(people);
    }

    public List<Transaction> getAllTransactions() {
        return db.getTransactionDao().getAll();
    }
    public List<People> getAllPeoples() {
        return db.getPeopleDao().getAll();
    }
    public List<Account> getAllAccounts() {
        return db.getAccountDao().getAll();
    }
    public List<NotificationInfo> getAllNotifyInfo() {
        return db.getNotifyInfoDao().getAll();
    }

    public Optional<People> findPeopleById(Integer id) {
        return Optional.ofNullable(db.getPeopleDao().findById(id));
    }
    public Optional<Transaction> findTrnById(int trn_id) {
        return Optional.ofNullable(db.getTransactionDao().findById(trn_id));
    }

    public AppDatabase getDb() {
        return db;
    }

    //region DataImage delegate
    private final DataImage dataImage = new DataImageImpl();

    public Optional<Drawable> loadAppIcon(String pack) {
        return dataImage.loadAppIcon(pack);
    }

    public Optional<Bitmap> loadImg(String name) {
        return dataImage.loadImg(name);
    }

    public String saveImg(Bitmap bitmap) {
        return dataImage.saveImg(bitmap);
    }
    //endregion
}
