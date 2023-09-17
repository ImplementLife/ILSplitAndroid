package com.impllife.split.data.jpa.convert;

import androidx.room.TypeConverter;
import com.impllife.split.data.jpa.entity.type.BudgetPeriod;

public class BudgetPeriodConverter {
    @TypeConverter
    public static BudgetPeriod toBudgetPeriod(int key) {
        return BudgetPeriod.getByKey(key);
    }

    @TypeConverter
    public static int toKey(BudgetPeriod period) {
        return period.getKey();
    }
}
