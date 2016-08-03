package com.atongmu.util;

/**
 * 用于字符串进行分割的协议
 * <p/>
 * 实体类需要将传输的数据存储成字符串数组的形式，之后在调用此类中的函数
 *
 * @ClassName: SeparatorProtocol
 * @author: maofagui
 * @date: 2016年2月26日
 */
public class SeparatorProtocol {
    public static final String SEPARATOR = "^|^";
    public static final String SEPARATOR_REG_EXP = "(\\^\\|\\^)";
    public static final String REPLACE_STRING = " ";

    /**
     * 将字符串数组转换成用特定分隔符分割的字符串
     *
     * @param strArr
     * @return
     */
    public static String createMsg(String[] strArr) {
        if (strArr.length < 1) return null;
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < strArr.length - 1; i++) {
            res.append(escapeSeparator(strArr[i]));
            res.append(SEPARATOR);
        }
        res.append(escapeSeparator(strArr[strArr.length - 1]));
        return res.toString();
    }

    /**
     * 用特定分隔符将字符串分割成字符串数组
     *
     * @param msg
     * @return
     */
    public static String[] parseMsg(String msg) {
        if (null == msg) return null;
        return msg.split(SEPARATOR_REG_EXP);
    }

    /**
     * 替换原来字符串中的特定的字符串
     *
     * @param str
     * @return
     */
    public static String escapeSeparator(String str) {
        return str.replaceAll(SEPARATOR_REG_EXP, REPLACE_STRING);
    }

    /**
     * 检验字符串中是否存在特定的分隔符
     *
     * @param
     * @return
     */
    public static boolean existSeparator(String str) {
        return str.matches(SEPARATOR_REG_EXP);
    }

}
