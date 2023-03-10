package com.impllife.split.data.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "il_people")
public class People extends EntityWithId {
    @Column
    private int accountId;
    @Column
    private String pseudonym;
    @Column
    private Date dateUpdate;

    //region get & set

    public Date getDateUpdate() {
        return dateUpdate;
    }
    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

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

    //endregion
}
