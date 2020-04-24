package com.xiaochen.common.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author zlc
 * @date 2018/11/27
 */
public class DateUtil {

    private DateUtil() {

    }

    private final static String PATTERN = "yyyy-MM-dd hh:mm:ss";
    private final static String YEAR_MONTH_DAY = "yyyy-MM-dd";

    /**
     * 使用用户格式提取字符串日期
     *
     * @param time 日期long类型
     * @return
     */
    public static String getStringTime(long time) {
        if (time == 0) {
            return "";
        }
        try {
            Date date = new Date(time);
            SimpleDateFormat df = new SimpleDateFormat(PATTERN, Locale.CHINA);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param time 日期long类型
     * @return
     */
    public static String getYearMonthDay(long time) {
        if (time == 0) {
            return "";
        }
        try {
            Date date = new Date(time);
            SimpleDateFormat df = new SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINA);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {

        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatHome(long date) {
        if (date == 0) {
            return "";
        }
        try {
            SimpleDateFormat dfUp = new SimpleDateFormat("MM月dd日 上午 HH:mm", Locale.CHINA);
            SimpleDateFormat dfDown = new SimpleDateFormat("MM月dd日 下午 HH:mm", Locale.CHINA);

            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(date);
            int apm = instance.get(Calendar.AM_PM);
            if (apm == 0) {
                return dfUp.format(date);
            }
            return dfDown.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //由出生日期获得年龄
    public static int getAge(Date birthDay) {
        //生日
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthDay);
        //当前
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthCal)) {
            LogUtil.e("现在日期比生日还小，现在日期个数错误");
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);

        int yearBirth = birthCal.get(Calendar.YEAR);
        int monthBirth = birthCal.get(Calendar.MONTH);
        int dayBirth = birthCal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow > monthBirth) {
            return age;
        } else if (monthNow < monthBirth) {
            return age - 1;
        } else if (dayNow >= dayBirth) {
            return age;
        } else {
            return age - 1;
        }
    }

}
