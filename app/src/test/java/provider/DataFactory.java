package provider;

import java.util.Calendar;

/**
 * Created by owenh on 2016/8/4.
 */

public class DataFactory {

    public static void getDateTimeNow(String[] args) {

        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);//获取年份
        int month=ca.get(Calendar.MONTH);//获取月份
        int day=ca.get(Calendar.DATE);//获取日
        int minute=ca.get(Calendar.MINUTE);//分
        int hour=ca.get(Calendar.HOUR);//小时
        int second=ca.get(Calendar.SECOND);//秒
        int WeekOfYear = ca.get(Calendar.DAY_OF_WEEK);


    }

}
