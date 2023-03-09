package com.impllife.split.service;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.impllife.split.ui.MainActivity;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

import java.lang.annotation.Annotation;
import java.util.*;

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

    public static List<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotationClass) {
        List<Class<?>> classes = new ArrayList<>();
        Context context = MainActivity.getInstance();

        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();

            DexClassLoader dexClassLoader = new DexClassLoader(applicationInfo.sourceDir, context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            String appPackageName = context.getApplicationContext().getPackageName();
            DexFile dexFile = new DexFile(applicationInfo.sourceDir);
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();
                if (!className.startsWith(appPackageName)) continue;
                try {
                    Class<?> clazz = dexClassLoader.loadClass(className);
                    if (clazz.isAnnotationPresent(annotationClass)) {
                        classes.add(clazz);
                    }
                } catch (Throwable t) {
                    Log.e("getAnnotatedClasses" + className, t.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}
