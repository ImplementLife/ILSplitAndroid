package com.impllife.split.data.jpa.provide;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

public interface RestDao<K, T extends WithId<K>> {
    @Insert
    void insert(T t);
    @Update
    void update(T t);
    @Delete
    void delete(T t);
}
