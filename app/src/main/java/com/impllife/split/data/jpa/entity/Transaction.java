package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity
public class Transaction extends EntityWithId {
    private Date dateCreate;
    private String sum;
    private String description;

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
}
