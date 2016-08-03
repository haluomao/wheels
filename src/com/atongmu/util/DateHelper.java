package com.atongmu.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间操作帮助类
 * @author maofagui
 * @version 1.0
 */
public class DateHelper {
    /**
     * 获取上一个月的时间
     * @param date
     * @return
     */
    public static Date getLastMonthDate(Date date) {
        return getDateWithOffset(date, 0, -1, 0);
    }

    /**
     * 获取输入日期前后的时间，参数分别是日、月、年的偏移值。参数offset若为负数，则是向前获取
     * @param date
     * @param dayOffset
     * @param monthOffset
     * @param yearOffset
     * @return
     */
    public static Date getDateWithOffset(Date date, int dayOffset, int monthOffset, int yearOffset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, dayOffset);
        cal.add(Calendar.MONTH, monthOffset);
        cal.add(Calendar.YEAR, yearOffset);
        return cal.getTime();
    }
}
