package com.impllife.split.data.jpa.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "il_transaction")
public class Transaction extends EntityWithId {
    private Date dateCreate;
    private String sum;
    private String description;

    @ColumnInfo(defaultValue = "0")
    private int fromPeopleId;
    @ColumnInfo(defaultValue = "0")
    private int toPeopleId;

    @ColumnInfo(defaultValue = "0")
    private int fromAccountId;
    @ColumnInfo(defaultValue = "0")
    private int toAccountId;

    private transient People fromPeople;
    private transient People toPeople;

    private transient Account fromAccount;
    private transient Account toAccount;

    //region get & set

    public int getFromPeopleId() {
        return fromPeopleId;
    }
    public void setFromPeopleId(int fromPeopleId) {
        this.fromPeopleId = fromPeopleId;
    }

    public int getToPeopleId() {
        return toPeopleId;
    }
    public void setToPeopleId(int toPeopleId) {
        this.toPeopleId = toPeopleId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }
    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }
    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public People getFromPeople() {
        return fromPeople;
    }
    public void setFromPeople(People fromPeople) {
        this.fromPeople = fromPeople;
    }

    public People getToPeople() {
        return toPeople;
    }
    public void setToPeople(People toPeople) {
        this.toPeople = toPeople;
    }

    public Account getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getSum() {
        return sum;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //endregion
}
