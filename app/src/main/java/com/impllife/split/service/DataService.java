package com.impllife.split.service;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.AppDatabase;
import com.impllife.split.ui.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataService {
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
    private final Map<String, Drawable> appIconMemoCash = new HashMap<>();

    public void insert(Transaction transactions) {
        db.getTransactionDao().insert(transactions);
    }
    public void insert(People people) {
        db.getPeopleDao().insert(people);
    }
    public void insert(Account account) {
        db.getAccountDao().insert(account);
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

    public Optional<Drawable> loadAppIcon(String pack) {
        if (appIconMemoCash.containsKey(pack)) {
            return Optional.ofNullable(appIconMemoCash.get(pack));
        }
        Drawable drawable = null;
        try {
            PackageManager pm = MainActivity.getInstance().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(pack, 0);
            drawable = ai.loadIcon(pm);
            appIconMemoCash.put(pack, drawable);
        } catch (PackageManager.NameNotFoundException ignored) {
            appIconMemoCash.put(pack, null);
            Log.w("DataService.loadAppIcon", "can't load another icon of app: " + pack);
        }
        return Optional.ofNullable(drawable);
    }
}
