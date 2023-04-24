package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.impllife.split.data.jpa.provide.WithId;

@Entity(tableName = "il_notification")
public class NotificationInfo implements WithId<Integer> {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String appPackage;
    private String appName;
    private String title;
    private String text;
    private int iconResId;

    //region get & set

    public int getIconResId() {
        return iconResId;
    }
    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getAppPackage() {
        return appPackage;
    }
    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    @Override
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    //endregion

}
