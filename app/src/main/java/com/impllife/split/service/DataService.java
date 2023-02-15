package com.impllife.split.service;

import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.DaoFactory;

import java.util.List;

public class DataService {
    private static DataService instance;
    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }
    private DataService() {}

    private final DaoFactory repo = new DaoFactory();

    public void insert(Transaction transactions) {
        repo.getTransactionDao().insert(transactions);
    }
    public void insert(People people) {
        repo.getPeopleDao().insert(people);
    }

    public void update(Transaction transactions) {
        repo.getTransactionDao().update(transactions);
    }
    public void update(People people) {
        repo.getPeopleDao().update(people);
    }

    public void delete(Transaction transactions) {
        repo.getTransactionDao().delete(transactions);
    }
    public void delete(People people) {
        repo.getPeopleDao().delete(people);
    }

    public List<Transaction> getAllTransactions() {
        return repo.getTransactionDao().getAll();
    }
    public List<People> getAllPeoples() {
        return repo.getPeopleDao().getAll();
    }
}
