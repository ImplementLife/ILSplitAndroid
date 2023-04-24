package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.List;

@Dao
public interface PeopleDao extends RestDao<Integer, People> {
    @Query("SELECT * FROM `people` WHERE id = :id")
    People findById(int id);

    @Query("DELETE FROM `people` WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM `people`")
    List<People> getAll();
}
