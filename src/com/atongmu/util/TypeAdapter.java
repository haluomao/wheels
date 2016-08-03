package com.atongmu.util;

/**
 * 类型转换类
 * date: 2016/8/1
 *
 * @author maofagui
 * @version 1.0
 */
public class TypeAdapter {
    /**
     * 时间类型转换 java.util.Date->java.sql.Date
     * @param date
     * @return
     */
    public static java.sql.Date utilDateToSqlDate (java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 时间类型转换 java.sql.Date->java.util.Date
     * @param date
     * @return
     */
    public static java.util.Date sqlDateToUtilDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }

}
