package com.impllife.split.service;

import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.DBProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    //region singleton

    private static DataService instance;
    private final DBProvider dbProvider;

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }
    private DataService() {
        dbProvider = new DBProvider();
    }

    //endregion

    public void save(Object entity) {
        dbProvider.save(entity);
    }

    public void delete(People people) {
        try {
            dbProvider.getDao(People.class).delete(people);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactions() {
        try {
            return dbProvider.getDao(Transaction.class).queryBuilder()
                .orderBy("dateCreate", false)
                .query();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    public List<People> getAllPeoples() {
        try {
            return dbProvider.getDao(People.class).queryForAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    public List<Account> getAllAccounts() {
        try {
            return dbProvider.getDao(Account.class).queryForAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
