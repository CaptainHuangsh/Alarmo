package com.example.owenh.alarmo.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by owenh on 2016/8/6.
 */

public class DateDay {
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;

    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "Sun";
        } else if ("2".equals(mWay)) {
            mWay = "Mon";
        } else if ("3".equals(mWay)) {
            mWay = "Tue";
        } else if ("4".equals(mWay)) {
            mWay = "Wen";
        } else if ("5".equals(mWay)) {
            mWay = "Tur";
        } else if ("6".equals(mWay)) {
            mWay = "Fri";
        } else if ("7".equals(mWay)) {
            mWay = "Sat";
        }
        return mYear + "-" + mMonth + "-" + mDay + "-" + mWay;
    }
}
