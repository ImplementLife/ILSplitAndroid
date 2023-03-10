package com.impllife.split.service;

import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.DBProvider;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public Optional<People> findPeopleById(Long trn_id) {
        People entity = null;
        try {
            Dao<People, Long> dao = dbProvider.getDao(People.class);
            entity = dao.queryForId(trn_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }

    public Optional<Transaction> findTrnById(Long trn_id) {
        Transaction entity = null;
        try {
            Dao<Transaction, Long> dao = dbProvider.getDao(Transaction.class);
            entity = dao.queryForId(trn_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }
}
