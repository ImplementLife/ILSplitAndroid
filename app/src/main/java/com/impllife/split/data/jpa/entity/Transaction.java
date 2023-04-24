package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "il_transaction")
public class Transaction extends EntityWithId {
    private Date dateCreate;
    private String sum;
    private String description;

    private transient People people;

    //region get & set

    public People getPeople() {
        return people;
    }
    public void setPeople(People people) {
        this.people = people;
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
