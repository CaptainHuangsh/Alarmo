package com.example.owenh.alarmo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.common.DoubleClick;
import com.example.owenh.alarmo.services.RingService;
import com.zhy.autolayout.AutoLayoutActivity;

import com.example.owenh.alarmo.util.DateDayUtil;

/**
 * Created by owenh on 2016/8/5.
 */

public class WatchActivity extends AutoLayoutActivity {

    public static int WATCH_STATUS = 0;

    private TextView mVTime;
    private TextView mSec;
    private TextView mDay;
    DateDayUtil mDateDay = new DateDayUtil();
    private static final int MSG_KEY_1 = 1;
    PowerManager powerManager = null;
    PowerManager.WakeLock wakeLock = null;
    //将字体文件保存在assets/fonts/目录下，创建Typeface对象
    Typeface typeFace;
    String textColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = WatchActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //强制横屏
        setContentView(R.layout.activity_watch);
        findview();
        init();
        new WatchActivity.TimeThread().start();
        //使屏幕常亮 在低版本中并不起作用（4.4）
        this.powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(PowerManager
                .FULL_WAKE_LOCK, "My Lock");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//另一种屏幕常亮的方法
        //仍未解决4.4api19版本的屏幕不能常亮的问题
    }

    public void init() {
        WATCH_STATUS = 1;
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        textColor = preferences.getString("pref_text_color","#ff00ddff");
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/digifaw.ttf");
        //字体
        mVTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (DoubleClick.check()) {
                    if (mDay.getVisibility() == View.GONE) {
                        mDay.setVisibility(View.VISIBLE);
                    } else {
                        mDay.setVisibility(View.GONE);
                    }
//                }
            }
        });
        Intent intent = new Intent(this, RingService.class);
        startService(intent);
    }


    public void findview() {
        mVTime = (TextView) findViewById(R.id.alarm_vtime);
        mSec = (TextView) findViewById(R.id.alarm_vsec);
        mDay = (TextView) findViewById(R.id.alarm_day);
    }

    /**
     * 建立一个线程，每秒钟刷新一次
     */
    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = MSG_KEY_1;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_KEY_1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm", sysTime);
                    CharSequence sysTimeStrsec = DateFormat.format("ss", sysTime);
                    mVTime.setText(sysTimeStr + "");
                    mSec.setText(" " + sysTimeStrsec);
                    mDay.setText(mDateDay.StringData() + "");
                    mVTime.setTypeface(typeFace);
                    mSec.setTypeface(typeFace);
                    mDay.setTypeface(typeFace);
                    mVTime.setTextColor(Color.parseColor(textColor));
                    mSec.setTextColor(Color.parseColor(textColor));
                    mDay.setTextColor(Color.parseColor(textColor));
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        this.wakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.wakeLock.release();
    }

    @Override
    protected void onDestroy() {
        WATCH_STATUS = 0;
        super.onDestroy();
//        Intent intent = new Intent(this, RingService.class);
//        stopService(intent);
    }

    @Override
    public void onBackPressed() {
        if (!DoubleClick.check()) {
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
