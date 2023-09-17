package com.impllife.split.data.jpa.convert;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date != null ? date.getTime() : null;
    }
}
