package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.List;

@Dao
public abstract class PeopleDao implements RestDao<Integer, People> {
    @Query("SELECT * FROM `people` WHERE id = :id")
    public abstract People findById(int id);

    @Query("DELETE FROM `people` WHERE id = :id")
    public abstract void deleteById(int id);

    @Query("SELECT * FROM `people`")
    public abstract List<People> getAll();
}
