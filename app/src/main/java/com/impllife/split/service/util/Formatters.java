package com.impllife.split.service.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Formatters {
    private static final SimpleDateFormat FORMAT_dd_MM_yyyy = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat FORMAT_dd_MM = new SimpleDateFormat("dd.MM");
    private static final SimpleDateFormat FORMAT_HH_mm = new SimpleDateFormat("HH:mm");

    public static String formatDDMMYYYY(Date date) {
        return FORMAT_dd_MM_yyyy.format(date);
    }
    public static String formatDDMM(Date date) {
        return FORMAT_dd_MM.format(date);
    }
    public static String formatHHMM(Date date) {
        return FORMAT_HH_mm.format(date);
    }
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
