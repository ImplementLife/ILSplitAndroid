package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.List;

@Dao
public interface TransactionDao extends RestDao<Integer, Transaction> {
    @Query("SELECT * FROM il_transaction WHERE id = :id")
    Transaction findById(int id);

    @Query("DELETE FROM il_transaction WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM il_transaction ORDER BY dateCreate DESC")
    List<Transaction> getAll();

    @Query("SELECT * FROM il_transaction ORDER BY dateCreate DESC")
    List<Transaction> getAllEager();
}
