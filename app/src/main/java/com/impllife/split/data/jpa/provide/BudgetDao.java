package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.Budget;

@Dao
public interface BudgetDao extends RestDao<Integer, Budget> {
    @Query("SELECT * FROM il_budget WHERE id = :id")
    Budget findById(int id);
}
