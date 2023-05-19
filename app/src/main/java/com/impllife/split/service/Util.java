package com.impllife.split.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Util {
    private static final Calendar CALENDAR = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();

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

    public static String date() {
        return dateFormat.format(new Date());
    }

    public static String date(Object text) {
        return String.format("%s %s", dateFormat.format(new Date()), text.toString());
    }

    /**
     * Returns {@code true} if the given string is blank, {@code false} otherwise.
     * A string is considered blank if it is empty (i.e., has a length of 0), contains only whitespace characters
     * (e.g., spaces, tabs, line breaks), or is {@code null}.
     *
     * @param string the string to check for blankness (may be {@code null})
     * @return {@code true} if the string is blank, {@code false} otherwise
     */
    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * Returns {@code true} if the given string is empty, {@code false} otherwise.
     * A string is considered empty if it is empty (i.e., has a length of 0) or is {@code null}.
     *
     * @param string the string to check for emptiness (may be {@code null})
     * @return {@code true} if the string is empty, {@code false} otherwise
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static Bundle bundle(String key, Object value) {
        Bundle bundle = new Bundle();
        Class<?> valueClass = value.getClass();
        if (valueClass.equals(Integer.class)) {
            bundle.putInt(key, (Integer) value);
        } else if (valueClass.equals(Boolean.class)) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (valueClass.equals(String.class)) {
            bundle.putString(key, (String) value);
        } else {
            Log.w("bundle", "value [" + valueClass.getTypeName() + "] type don't support");
        }
        return bundle;
    }
}
