package com.impllife.split.data.jpa.entity;

import androidx.room.Entity;
import com.impllife.split.data.jpa.entity.type.BudgetPeriod;

import java.math.BigDecimal;

@Entity(tableName = "il_budget")
public class Budget extends EntityWithId {
    private String name;
    private BigDecimal sumForPeriod;
    private BudgetPeriod period;
    private boolean showInTransaction;

    //region get & set
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSumForPeriod() {
        return sumForPeriod;
    }
    public void setSumForPeriod(BigDecimal sumForPeriod) {
        this.sumForPeriod = sumForPeriod;
    }

    public BudgetPeriod getPeriod() {
        return period;
    }
    public void setPeriod(BudgetPeriod period) {
        this.period = period;
    }
    public boolean isPeriod(BudgetPeriod period) {
        return this.period == period;
    }

    public boolean isShowInTransaction() {
        return showInTransaction;
    }
    public void setShowInTransaction(boolean showInTransaction) {
        this.showInTransaction = showInTransaction;
    }
    //endregion
}
