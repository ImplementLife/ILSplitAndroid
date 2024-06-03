package com.impllife.split.service.util.date;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    public static DateRange getCurrentWeekDateRange() {
        return getWeekDateRange(getCurrentYear(), getCurrentWeek());
    }

    public static DateRange getWeekDateRange(int year, int weekNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date endDate = calendar.getTime();

        return new DateRange(startDate, endDate);
    }

    public static int getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static boolean isToday(Date date) {
        Date currentDate = new Date();
        return isSameDay(currentDate, date);
    }

    public static boolean isYesterday(Date date) {
        Date currentDate = new Date();
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        Date yesterdayDate = new Date(currentDate.getTime() - oneDayInMillis);
        return isSameDay(yesterdayDate, date);
    }

    public static boolean isTomorrow(Date date) {
        Date currentDate = new Date();
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        Date nextDate = new Date(currentDate.getTime() + oneDayInMillis);
        return isSameDay(nextDate, date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .equals(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);

        return year1 == year2 && month1 == month2;
    }

    public static boolean isSameWeek(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int week1 = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int week2 = calendar.get(Calendar.WEEK_OF_YEAR);

        return year1 == year2 && week1 == week2;
    }

    public static Date getDay(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.DAY_OF_YEAR, day);
        calendar.set(Calendar.YEAR, year);

        return calendar.getTime();
    }
}
