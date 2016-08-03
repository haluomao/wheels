package com.atongmu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化帮助类
 *
 * @author maofagui
 * @version 1.0
 */
public class DateFormatHelper {


    /**
     * 时间格式化为：yyyy-MM-dd
     * @param dateLong
     * @return
     */
    public static String formatDateByLongWith(Long dateLong) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(dateLong));
    }

    /**
     * 时间格式化为：yyyyMMdd
     * @param dateLong
     * @return
     */
    public static String SimpleDateToString(Long dateLong) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date(dateLong));
    }

    /**
     * 时间格式化为：yyyy-MM-dd HH:mm:ss
     * @param dateLong
     * @return
     */
    public static String formatDateByLong(Long dateLong) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(dateLong));
    }

    /**
     * 时间格式化为：yyyyMMddHHmmss
     * @param dateLong
     * @return
     */
    public static String DateToString(Long dateLong) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date(dateLong));
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss"格式的时间字符串解析日期
     * @param stringDate
     * @return
     */
    public static Date StringToDate(String stringDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parseDate = simpleDateFormat.parse(stringDate);
        return parseDate;
    }

    /**
     * 将"yyyy/MM/dd HH:mm:ss"、"yyyy\MM\dd HH:mm:ss"、"yyyy-MM-dd HH:mm:ss"格式的时间字符串解析日期
     * @param stringDate
     * @return
     * @throws ParseException
     */

    public static Date StringToDateAdapter(String stringDate) throws ParseException {
        stringDate = stringDate.replaceAll("/", "-");
        stringDate = stringDate.replaceAll("\\\\", "-");
        return StringToDate(stringDate);
    }

    /**
     * 将date格式化为：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 将date格式化为：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateToDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间解析为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date formatStringByDate(Date date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringdate = simpleDateFormat.format(date);
        Date parse = simpleDateFormat.parse(stringdate);
        return parse;
    }

    /**
     * 将时间格式化为自定义格式
     * @param date
     * @param formatStr
     * @return
     */
    public static String selfDefinedDateToString(Date date, String formatStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        return simpleDateFormat.format(date);
    }

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
