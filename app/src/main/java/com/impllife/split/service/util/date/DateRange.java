package com.impllife.split.service.util.date;

import java.util.Date;

public class DateRange {
    private Date startDate;
    private Date endDate;

    public DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean inRange(Date date) {
        return !date.before(startDate) && !date.after(endDate);
    }

    public boolean isBefore(Date date) {
        return startDate.before(date);
    }

    public boolean isAfter(Date date) {
        return endDate.after(date);
    }
}
