package com.xiaochen.common.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : Stirng类型工具类
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 由字符串得到一个不为null的字符串
     * @param string
     * @return
     */
    public static String getNotNullString(String string){
        return TextUtils.isEmpty(string) ? "" : string;
    }

    /**
     * 字符串返回值是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 两个字符串是否相等
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isEqual(String s1, String s2) {
        return TextUtils.equals(s1, s2);
    }

    /**
     * 判断一个字符串是不是只由数字组成
     *
     * @param str
     * @return
     */
    public static boolean isDigitsOnly(String str) {
        return TextUtils.isDigitsOnly(str);
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String substring(String str, int start, int end) {
        return str.substring(start, end);
    }

    /**
     * 字符串反转
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String reverse(String str, int start, int end) {
        return TextUtils.getReverse(str, start, end).toString();
    }

    public static String reverse(String str) {
        return reverse(str, 0, str.length());
    }

    /**
     * 大小写转换
     *
     * @param str
     * @param isUpper
     * @return
     */
    public static String caseConversion(String str, boolean isUpper) {
        if (isUpper) {
            return str.toLowerCase();
        } else {
            return str.toUpperCase();
        }
    }


    /**
     * 对象转json
     *
     * @param obj
     * @return
     */
    public static String beanToJson(Object obj) {
        final Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * json转bean对象
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> tClass) {
        final Gson gson = new Gson();
        return (T) gson.fromJson(json, tClass);
    }

    //由出生日期获得年龄
    public static int getAge(String birth) throws Exception {
        int age = 18;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date birthDay = sdf.parse(birth);
            Calendar cal = Calendar.getInstance();
            if (cal.before(birthDay)) {
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(birthDay);

            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            age = yearNow - yearBirth;

            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;
                } else {
                    age--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }

    /**
     * long类型ip地址转换为String类型ip地址
     *
     * @param ip long类型ip
     * @return String 类型ip地址
     */
    public static String longToIPAddress(long ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip & 0x00ff)
                .append(".")
                .append((ip & 0x00ffff) >> 8)
                .append(".")
                .append((ip & 0xffffff) >> 16)
                .append(".")
                .append(ip >> 24);
        return sb.toString();
    }

    /**
     * 获取wifi信号强度
     */
    public static String getWifiLevel(int level) {
        //根据获得的信号强度发送信息
        if (level <= 0 && level >= -50) {
            return "强";
        } else if (level < -50 && level >= -70) {
            return "良好";
        } else if (level < -70 && level >= -80) {
            return "一般";
        } else if (level < -80 && level >= -100) {
            return "较差";
        } else {
            return "无信号";
        }
    }
}
