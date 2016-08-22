package com.atongmu.util;

import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * date: 2016/7/31
 *
 * @author maofagui
 * @version 1.0
 */
public class RegExpHelper {
    /**
     * 是否有效手机号码
     * @param phone
     * @return
     */
    public static boolean isPhoneNum(String phone){
        if(StringUtils.isEmpty(phone))
            return false;

        Pattern pattern = Pattern.compile("^1\\d{10}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 是否有效邮箱
     * @param src
     * @return
     */
    public static boolean isEmail(String src){
        if(StringUtils.isEmpty(src))
            return false;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    /**
     * 是否实名，2到5个汉字
     * @param realCNName
     * @return
     */
    public static boolean isRealCNName(String realCNName) {
        if (StringUtils.isEmpty(realCNName))
            return false;

        int length = realCNName.length();
        if (length < 1 || length > 5)
            return false;

        Pattern pattern = Pattern.compile("^[\\u3E00-\\u9FA5\\·]{2,5}$");
        Matcher matcher = pattern.matcher(realCNName);
        return matcher.matches();
    }

    /**
     * 确定密码强度等级
     * @param passwd
     * @return 密码强度0默认值或者未设置，1弱，2中，3强
     */
    public static int getPWStrength(String passwd){
        if (StringUtils.isEmpty(passwd))
            return 0;

        int pwStrength = 0;
        if (passwd.length() < 8) { //密码长度为第一优先级判断，长度<8，弱口令
            pwStrength = 1;
        }
        else {
            boolean hasLetter = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE).matcher(passwd).matches();
            boolean hasNumber = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE).matcher(passwd).matches();
            boolean hasSymbols = Pattern.compile("^[!@#_]+$", Pattern.CASE_INSENSITIVE).matcher(passwd).matches();

            if (hasLetter) pwStrength++;
            if (hasNumber) pwStrength++;
            if (hasSymbols) pwStrength++;
        }
        return pwStrength;
    }
    /**
     * 验证身份证格式
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard))
            return false;
        Pattern pattern = Pattern.compile("^\\d{15}|\\d{17}[\\dXx]$");
        Matcher matcher = pattern.matcher(idCard);
        return matcher.matches();
    }

    /**
     * 验证身份证地址
     * @param idCard
     * @return
     */
    public static boolean validateAddress(String idCard) {
        String addressCode = idCard.substring(0, 2);
        final String address = "11x22x35x44x53x12x23x36x45x54x13x31x37x46x61x14x32x41x50x62x15x33x42x51x63x21x34x43x52x64x65x71x81x82x91";

        if (address.indexOf(addressCode) == -1)
            return false;
        return true;
    }

    /**
     * 验证身份证生日
     * @param idCard
     * @return
     */
    public static boolean validateBirthday(String idCard) {
        if (StringUtils.isEmpty(idCard))
            return false;
        int year = Integer.valueOf(idCard.substring(6,10)).intValue();
        int month = Integer.valueOf(idCard.substring(10, 12)).intValue();
        int day = Integer.valueOf(idCard.substring(12, 14)).intValue();

        Calendar calendar =Calendar.getInstance();
        int cYear = calendar.get(Calendar.YEAR);
        int cMonth = calendar.get(Calendar.MONTH);
        int cDay = calendar.get(Calendar.DATE);

        if (year <= 0 || year > cYear)
            return false;
        if (year == cYear) {
            if (month > cMonth)
                return false;
            if (day > cDay)
                return false;
        }
        if (month <= 0 || month > 12)
            return false;

        final int[] days = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        boolean isLeapYear = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));

        if (day <= 0 || day > days[month])
            return false;
        if (month == 2 && day == 29 && !isLeapYear)
            return false;
        return true;
    }

    /**
     * 验证身份证校验码
     * @param idCard
     * @return
     */
    @Deprecated
    public static boolean validateIdCardCode(String idCard) {
        if (idCard.length() == 15)
            return true;

        String[] varifyCodes = "1,0,x,9,8,7,6,5,4,3,2".split(",");
        String[] tailCodes = "7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2".split(",");
        char[] idCardCodes = idCard.toCharArray();

        int sum = 0;
        for (int i=0; i < 17; i++) {
            sum += Integer.parseInt(tailCodes[i]) * Integer.parseInt(String.valueOf(idCardCodes[i]));
        }

        if (!String.valueOf(idCardCodes[17]).equals(varifyCodes[sum % 11]))
            return false;
        return true;
    }


}
