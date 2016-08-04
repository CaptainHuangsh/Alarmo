package com.example.owenh.alarmo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    private TextView mDate;
    private TextView mTime;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mMinute;
    private int mHour;
    private int mSecond;
    private int mWeekOfYear;
    Calendar  mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        findview();
        init();
    }
    public void init(){
        getDateTimeNow();
        mDate.setText(""+mCalendar.getTime());
        mTime.setText(mYear +"年"+ mMonth +"月"+ mDay + "日"+
        mHour+"时"+mMinute+"分"+mSecond+"秒"+mWeekOfYear+"周");

    }
    public void findview(){
        mDate = (TextView)findViewById(R.id.alarm_date);
        mTime = (TextView)findViewById(R.id.alarm_time);
    }

    public void getDateTimeNow() {

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);//获取年份
        mMonth= mCalendar.get(Calendar.MONTH);//获取月份
        mDay= mCalendar.get(Calendar.DATE);//获取日
        mMinute= mCalendar.get(Calendar.MINUTE);//分
        mHour= mCalendar.get(Calendar.HOUR);//小时
        mSecond= mCalendar.get(Calendar.SECOND);//秒
        mWeekOfYear = mCalendar.get(Calendar.DAY_OF_WEEK);


    }

}
