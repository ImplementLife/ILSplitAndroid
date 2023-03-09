package com.impllife.split.data.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "il_account")
public class Account extends EntityWithId {
    @Column
    private String name;
    @Column
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
