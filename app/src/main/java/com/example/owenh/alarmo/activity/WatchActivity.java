package com.example.owenh.alarmo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.common.DoubleClick;
import com.example.owenh.alarmo.services.RingService;
import com.example.owenh.alarmo.util.DateDayUtil;
import com.example.owenh.alarmo.util.SPUtils;
import com.example.owenh.alarmo.util.T;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by owenh on 2016/8/5.
 */

public class WatchActivity extends AutoLayoutActivity {

    public static int WATCH_STATUS = 0;

    private TextView mVTime;
    private TextView mSec;
    private TextView mDay;
    PowerManager mPowerManager = null;
    PowerManager.WakeLock wakeLock = null;
    private TextClock textClock;
    //将字体文件保存在assets/fonts/目录下，创建Typeface对象
    Typeface typeFace;
    String textColor;
    boolean is24Hours;

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
        findView();
        init();
//        new WatchActivity.TimeThread().start();
        mDay.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshTime();
            }
        }, 1000);
        //使屏幕常亮 在低版本中并不起作用（4.4）
        this.mPowerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.mPowerManager.newWakeLock(PowerManager
                .FULL_WAKE_LOCK, "Alarmo:My Lock");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//另一种屏幕常亮的方法
        //仍未解决4.4api19版本的屏幕不能常亮的问题
    }

    public void init() {
        WATCH_STATUS = 1;
        textColor = (String) SPUtils.getInstance().get(this, "pref_text_color", "#ff00ddff");
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/digifaw.ttf");
        is24Hours = (Boolean) SPUtils.getInstance().get(this, "hours_12_24", true);
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


    public void findView() {
        mVTime = (TextView) findViewById(R.id.alarm_text_time);
        mSec = (TextView) findViewById(R.id.alarm_text_second);
        mDay = (TextView) findViewById(R.id.alarm_day);
    }

    @Override
    protected void onStart() {
        WATCH_STATUS = 1;
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.wakeLock.release();
    }

    @Override
    protected void onStop() {
        WATCH_STATUS = 0;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        WATCH_STATUS = 0;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!DoubleClick.check()) {
            T.showShort(this, "再按一次退出");
        } else {
            finish();
        }
    }

    private void refreshTime() {
        long sysTime = System.currentTimeMillis();
        CharSequence sysTimeStr;
        if (is24Hours) {
            sysTimeStr = DateFormat.format("HH:mm", sysTime);
        } else {
            sysTimeStr = DateFormat.format("hh:mm", sysTime);
            //12小时制
        }
        CharSequence sysTimeStrSec = DateFormat.format("ss", sysTime);
        mVTime.setText(sysTimeStr);
        mSec.setText(sysTimeStrSec);
        mDay.setText(DateDayUtil.StringData());
        mVTime.setTypeface(typeFace);
        mSec.setTypeface(typeFace);
        mDay.setTypeface(typeFace);
        mVTime.setTextColor(Color.parseColor(textColor));
        mSec.setTextColor(Color.parseColor(textColor));
        mDay.setTextColor(Color.parseColor(textColor));
    }
}
