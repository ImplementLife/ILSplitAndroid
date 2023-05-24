package com.impllife.split.service;

import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.AccountDao;
import com.impllife.split.data.jpa.provide.PeopleDao;
import com.impllife.split.data.jpa.provide.TransactionDao;

import java.util.List;

public class TransactionService implements TransactionDao {
    private final TransactionDao trnDao;
    private final AccountDao accountDao;
    private final PeopleDao peopleDao;

    public TransactionService() {
        peopleDao = DataService.getInstance().getDb().getPeopleDao();
        accountDao = DataService.getInstance().getDb().getAccountDao();
        trnDao = DataService.getInstance().getDb().getTransactionDao();
    }

    public TransactionService(TransactionDao trnDao, AccountDao accountDao, PeopleDao peopleDao) {
        this.trnDao = trnDao;
        this.accountDao = accountDao;
        this.peopleDao = peopleDao;
    }

    /** Use before save
     * @param trn
     */
    private void fillIdByMappings(Transaction trn) {
        trn.setFromAccountId(trn.getFromAccount() != null ? trn.getFromAccount().getId() : 0);
        trn.setToAccountId(trn.getToAccount() != null ? trn.getToAccount().getId() : 0);

        trn.setFromPeopleId(trn.getFromPeople() != null ? trn.getFromPeople().getId() : 0);
        trn.setToPeopleId(trn.getToPeople() != null ? trn.getToPeople().getId() : 0);
    }

    /** Use before load
     * @param trn
     */
    private void fillMappings(Transaction trn) {
        trn.setFromAccount(accountDao.findById(trn.getFromAccountId()));
        trn.setToAccount(accountDao.findById(trn.getToAccountId()));

        trn.setFromPeople(peopleDao.findById(trn.getFromPeopleId()));
        trn.setToPeople(peopleDao.findById(trn.getToPeopleId()));
    }


    @Override
    public void insert(Transaction transaction) {
        fillIdByMappings(transaction);
        trnDao.insert(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        fillIdByMappings(transaction);
        trnDao.update(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        trnDao.delete(transaction);
    }

    @Override
    public Transaction findById(int id) {
        Transaction trn = trnDao.findById(id);
        fillMappings(trn);
        return trn;
    }

    @Override
    public void deleteById(int id) {
        trnDao.deleteById(id);
    }

    @Override
    public List<Transaction> getAll() {
        return trnDao.getAll();
    }
}
