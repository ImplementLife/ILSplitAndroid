package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.impllife.split.data.jpa.provide.WithId;

import java.util.Date;

@Entity(tableName = "il_notification")
public class NotificationInfo implements WithId<Integer> {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String appPackage;
    private String appName;
    private String title;
    private String text;
    private int iconResId;
    private Date postDate;

    //region get & set

    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

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

    //region object

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationInfo info = (NotificationInfo) o;

        if (getIconResId() != info.getIconResId()) return false;
        if (getAppPackage() != null ? !getAppPackage().equals(info.getAppPackage()) : info.getAppPackage() != null) return false;
        if (getAppName() != null ? !getAppName().equals(info.getAppName()) : info.getAppName() != null) return false;
        if (getTitle() != null ? !getTitle().equals(info.getTitle()) : info.getTitle() != null) return false;
        if (getText() != null ? !getText().equals(info.getText()) : info.getText() != null) return false;
        return getPostDate() != null ? getPostDate().equals(info.getPostDate()) : info.getPostDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getAppPackage() != null ? getAppPackage().hashCode() : 0;
        result = 31 * result + (getAppName() != null ? getAppName().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + getIconResId();
        result = 31 * result + (getPostDate() != null ? getPostDate().hashCode() : 0);
        return result;
    }

    //endregion
}
