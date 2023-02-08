package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idOnServer;

    private long dateCreate;
    private String sum;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdOnServer() {
        return idOnServer;
    }
    public void setIdOnServer(int idOnServer) {
        this.idOnServer = idOnServer;
    }

    public long getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getSum() {
        return sum;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }
}
