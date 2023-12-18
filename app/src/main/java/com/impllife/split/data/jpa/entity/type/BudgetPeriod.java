package com.impllife.split.data.jpa.entity.type;

public enum BudgetPeriod {
    DAY(0), WEEK(1), MONTH(2), QUARTER(3), YEAR(4);

    private final int key;

    BudgetPeriod(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public static BudgetPeriod getByKey(int key) {
        for (BudgetPeriod value : values()) {
            if (value.getKey() == key) return value;
        }
        return null;
    }
}
