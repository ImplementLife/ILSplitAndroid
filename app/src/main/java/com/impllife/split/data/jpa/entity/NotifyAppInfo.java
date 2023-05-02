package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.impllife.split.data.jpa.provide.WithId;

import java.util.List;

@Entity(tableName = "il_notify_app_info")
public class NotifyAppInfo implements WithId<Integer> {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String name;
    private String pack;
    private int iconResId;
    private boolean ignore;

    public NotifyAppInfo() {}
    public NotifyAppInfo(NotificationInfo notificationInfo) {
        this.name = notificationInfo.getAppName();
        this.pack = notificationInfo.getAppPackage();
        this.iconResId = notificationInfo.getIconResId();
    }

    //region get & set

    @Override
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPack() {
        return pack;
    }
    public void setPack(String pack) {
        this.pack = pack;
    }

    public int getIconResId() {
        return iconResId;
    }
    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public boolean isIgnore() {
        return ignore;
    }
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    //endregion
}
