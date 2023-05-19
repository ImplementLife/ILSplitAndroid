package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.Account;

import java.util.List;

@Dao
public interface AccountDao extends RestDao<Integer, Account> {
    @Query("SELECT * FROM il_account")
    List<Account> getAll();
    @Query("SELECT * FROM il_account WHERE id = :id")
    Account findById(int id);
}
