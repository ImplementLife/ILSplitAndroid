package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    // Добавление Person в бд
    @Insert
    void insert(Transaction... transactions);

    // Удаление Person из бд
    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM `transaction` WHERE id = :id")
    void deleteById(int id);

    // Получение всех Person из бд
    @Query("SELECT * FROM `transaction` order by dateCreate")
    List<Transaction> getAllTransactions();

    // Получение всех Person из бд с условием
//    @Query("SELECT * FROM person WHERE favoriteColor LIKE :color")
//    List<Transaction> getAllPeopleWithFavoriteColor(String color);
}
