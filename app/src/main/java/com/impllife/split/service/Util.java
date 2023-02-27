package com.impllife.split.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Util {
    private static final Calendar CALENDAR = Calendar.getInstance();

    /**
     * Returns true if the two given dates have the same day of month, month, and year.
     *
     * @param date1 the first date to compare
     * @param date2 the second date to compare
     * @return true if the two dates have the same day, month, and year, false otherwise
     * @throws NullPointerException if either date1 or date2 is null
     */
    public static boolean equalsDateByDMY(Date date1, Date date2) {
        Objects.requireNonNull(date1, "date1 must not be null");
        Objects.requireNonNull(date2, "date2 must not be null");
        CALENDAR.clear();

        CALENDAR.setTime(date1);
        int day1 = CALENDAR.get(Calendar.DAY_OF_MONTH);
        int month1 = CALENDAR.get(Calendar.MONTH);
        int year1 = CALENDAR.get(Calendar.YEAR);

        CALENDAR.setTime(date2);
        int day2 = CALENDAR.get(Calendar.DAY_OF_MONTH);
        int month2 = CALENDAR.get(Calendar.MONTH);
        int year2 = CALENDAR.get(Calendar.YEAR);

        return year1 == year2 && month1 == month2 && day1 == day2;
    }
}
