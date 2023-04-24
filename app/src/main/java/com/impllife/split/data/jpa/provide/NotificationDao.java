package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.NotificationInfo;

import java.util.List;

@Dao
public interface NotificationDao extends RestDao<Integer, NotificationInfo> {
    @Query("SELECT * FROM il_notification WHERE id = :id")
    NotificationInfo findById(int id);
    @Query("SELECT * FROM il_notification order by id desc")
    List<NotificationInfo> getAll();
}
