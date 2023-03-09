package com.impllife.split.data.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "il_transaction")
public class Transaction extends EntityWithId {
    @Column
    private Date dateCreate;
    @Column
    private String sum;
    @Column
    private String description;

    @ManyToOne
    private People people;

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
