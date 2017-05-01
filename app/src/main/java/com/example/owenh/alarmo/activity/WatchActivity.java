package com.example.owenh.alarmo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.provider.AlarmoDatabaseHelper;
import com.example.owenh.alarmo.services.RingService;
import com.zhy.autolayout.AutoLayoutActivity;

import util.DateDay;

/**
 * Created by owenh on 2016/8/5.
 */

public class WatchActivity extends AutoLayoutActivity {

    private int isRing = 0;
    private TextView mVTime;
    private TextView mSec;
    private TextView mDay;
    private MediaPlayer mMediaPlayer;
    DateDay mDateDay = new DateDay();
    private static final int MSG_KEY_1 = 1;
    private int ringTimes = 0;
    PowerManager powerManager = null;
    PowerManager.WakeLock wakeLock = null;
    //将字体文件保存在assets/fonts/目录下，创建Typeface对象
    Typeface typeFace;
    private AlarmoDatabaseHelper dbHelper;
    private String ringuri;
    SharedPreferences preferences;

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
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/digifaw.ttf");
        //字体
        mVTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDay.getVisibility() == View.GONE) {
                    mDay.setVisibility(View.VISIBLE);
                } else {
                    mDay.setVisibility(View.GONE);
                }
            }
        });
//        getRing();
        preferences = getApplicationContext().getSharedPreferences("Alarmo", MODE_PRIVATE);
        ringuri = preferences.getString("ringUri", "");
        if (ringuri.equals("")) {
            SharedPreferences.Editor editor = preferences.edit();
            ringuri = getSystemDefultRingtoneUri().toString();
            editor.putString("ringUri", ringuri);
            editor.commit();

        }
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
                    ringTimes = 0;
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm", sysTime);
                    CharSequence sysTimeStrsec = DateFormat.format("ss", sysTime);
                    mVTime.setText(sysTimeStr + "");
                    mSec.setText(" " + sysTimeStrsec);
                    mDay.setText(mDateDay.StringData() + "");
                    mVTime.setTypeface(typeFace);
                    mSec.setTypeface(typeFace);
                    mDay.setTypeface(typeFace);
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



    /**
     * 获取系统当前铃声
     */
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }
}
