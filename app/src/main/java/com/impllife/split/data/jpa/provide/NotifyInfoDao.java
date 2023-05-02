package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.NotificationInfo;

import java.util.List;

@Dao
public interface NotifyInfoDao extends RestDao<Integer, NotificationInfo> {
    @Query("SELECT * FROM il_notification WHERE id = :id")
    NotificationInfo findById(int id);
    @Query("SELECT * FROM il_notification order by id desc")
    List<NotificationInfo> getAll();
    @Query("SELECT * FROM il_notification WHERE appPackage = :appPackage")
    List<NotificationInfo> findAllByAppPackage(String appPackage);
    @Query("DELETE FROM il_notification WHERE appPackage = :appPackage")
    void deleteByAppPackage(String appPackage);



}
