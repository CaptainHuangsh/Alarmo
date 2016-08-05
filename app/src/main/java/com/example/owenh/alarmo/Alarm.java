package com.example.owenh.alarmo;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Calendar;
import android.os.Handler;

public class Alarm extends AppCompatActivity {

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
    Calendar  mCalendar;
    private static final int msgKey1 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_main);
        findview();
        init();
        new TimeThread().start();
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
        mVTime = (TextView)findViewById(R.id.alarm_vtime);
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
    /**
     * 建立一个线程，没秒钟刷新一次
     *
     * */
    public class TimeThread extends Thread {
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                    mVTime.setText(sysTimeStr+"");
                    break;

                default:
                    break;
            }
        }
    };

}
