package com.impllife.split.data.jpa.provide;

import androidx.room.Dao;
import androidx.room.Query;
import com.impllife.split.data.jpa.entity.NotifyAppInfo;

import java.util.List;

@Dao
public interface NotifyAppInfoDao extends RestDao<Integer, NotifyAppInfo> {
    @Query("SELECT * FROM il_notify_app_info")
    List<NotifyAppInfo> getAll();
    @Query("SELECT * FROM il_notify_app_info ORDER BY `ignore`")
    List<NotifyAppInfo> getAllOrderByIgnore();
    @Query("SELECT * FROM il_notify_app_info WHERE pack = :pack")
    NotifyAppInfo findByPackage(String pack);
    @Query("SELECT EXISTS(SELECT * FROM il_notify_app_info WHERE id = :id)")
    boolean isExist(Integer id);
    @Query("SELECT * FROM il_notify_app_info WHERE `ignore` = :ignore")
    List<NotifyAppInfo> findAllByIgnore(boolean ignore);

}
