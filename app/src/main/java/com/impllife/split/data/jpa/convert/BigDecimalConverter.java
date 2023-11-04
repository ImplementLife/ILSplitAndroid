package com.impllife.split.data.jpa.convert;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {
    @TypeConverter
    public static BigDecimal toBigDecimal(double bigDecimalAsDouble) {
        return BigDecimal.valueOf(bigDecimalAsDouble);
    }

    @TypeConverter
    public static double toDouble(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal.doubleValue() : 0;
    }
}
