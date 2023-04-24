package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.List;

@Dao
public abstract class TransactionDao implements RestDao<Integer, Transaction> {
    @Query("SELECT * FROM `transaction` WHERE id = :id")
    public abstract Transaction findById(int id);

    @Query("DELETE FROM `transaction` WHERE id = :id")
    public abstract void deleteById(int id);

    @Query("SELECT * FROM `transaction` order by dateCreate desc")
    public abstract List<Transaction> getAll();
}
