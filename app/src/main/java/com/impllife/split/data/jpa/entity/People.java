package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "il_people")
public class People extends EntityWithId {
    private int accountId;
    private String pseudonym;
    private Date dateUpdate;
    private String icon;

    //region get & set

    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPseudonym() {
        return pseudonym;
    }
    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }
    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    //endregion
}
