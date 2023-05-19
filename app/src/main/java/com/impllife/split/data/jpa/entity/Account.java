package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;

@Entity(tableName = "il_account")
public class Account extends EntityWithId {
    private String name;
    private double amount;
    private String imgName;

    //region get & set

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getImgName() {
        return imgName;
    }
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
    //endregion
}
