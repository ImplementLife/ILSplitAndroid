package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;

@Entity(tableName = "il_account")
public class Account extends EntityWithId {
    private String name;
    private double amount;

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

    //endregion
}
