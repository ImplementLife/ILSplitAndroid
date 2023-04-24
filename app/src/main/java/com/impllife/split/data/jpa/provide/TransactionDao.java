package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.List;

@Dao
public interface TransactionDao extends RestDao<Integer, Transaction> {
    @Query("SELECT * FROM `transaction` WHERE id = :id")
    Transaction findById(int id);

    @Query("DELETE FROM `transaction` WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM `transaction` order by dateCreate desc")
    List<Transaction> getAll();
}
