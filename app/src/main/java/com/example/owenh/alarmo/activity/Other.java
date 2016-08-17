package com.example.owenh.alarmo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.owenh.alarmo.R;

import java.util.Calendar;

/**
 * Created by owenh on 2016/8/5.
 */

public class Other extends Activity {
    private TextView mDate;
    private TextView mTime;
    private TextView mVTime;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mMinute;
    private int mHour;
    private int mSecond;
    private int mWeekOfYear;
    Calendar mCalendar;
    private static final int msgKey1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = Other.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.other);
        findview();
        init();
    }

    public void init() {
        getDateTimeNow();
        mDate.setText("" + mCalendar.getTime());
        mTime.setText(mYear + "年" + mMonth + "月" + mDay + "日" +
                mHour + "时" + mMinute + "分" + mSecond + "秒" + mWeekOfYear + "周");

    }

    public void findview() {
        mDate = (TextView) findViewById(R.id.alarm_date);
        mTime = (TextView) findViewById(R.id.alarm_time);
    }

    public void getDateTimeNow() {

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);//获取年份
        mMonth = mCalendar.get(Calendar.MONTH);//获取月份
        mDay = mCalendar.get(Calendar.DATE);//获取日
        mMinute = mCalendar.get(Calendar.MINUTE);//分
        mHour = mCalendar.get(Calendar.HOUR);//小时
        mSecond = mCalendar.get(Calendar.SECOND);//秒
        mWeekOfYear = mCalendar.get(Calendar.DAY_OF_WEEK);


    }

}
