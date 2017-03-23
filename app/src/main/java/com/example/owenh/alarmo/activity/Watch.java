package com.example.owenh.alarmo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.provider.AlarmoDatabaseHelper;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.IOException;

import util.DateDay;

/**
 * Created by owenh on 2016/8/5.
 */

public class Watch extends AutoLayoutActivity {

    private TextView mVTime;
    private TextView mSec;
    private TextView mDay;
    private MediaPlayer mMediaPlayer;
    DateDay mDateDay = new DateDay();
    private static final int msgKey1 = 1;
    private int ringTimes = 0;
    PowerManager powerManager = null;
    PowerManager.WakeLock wakeLock = null;
    //将字体文件保存在assets/fonts/目录下，创建Typeface对象
    Typeface typeFace;
    private AlarmoDatabaseHelper dbHelper;
    private String ringuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = Watch.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.watch);
        findview();
        init();
        new Watch.TimeThread().start();
        //使屏幕常亮
        this.powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(PowerManager
                .FULL_WAKE_LOCK, "My Lock");
    }

    public void init() {
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/digifaw.ttf");
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
    }

    public void getRing(){
        dbHelper = new AlarmoDatabaseHelper(this,"Alarmoyri.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询数据
        Cursor cursor = db.query("Alarmoyri",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                ringuri = cursor.getString(cursor.getColumnIndex("ringuri"));
                Log.d("Watch",ringuri);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void findview() {
        mVTime = (TextView) findViewById(R.id.alarm_vtime);
        mSec = (TextView) findViewById(R.id.alarm_vsec);
        mDay = (TextView) findViewById(R.id.alarm_day);
    }

    /**
     * 建立一个线程，没秒钟刷新一次
     */
    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
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
                case msgKey1:
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
                    if (DateFormat.format("mm:ss", sysTime).equals("00:00") || DateFormat.format("mm:ss", sysTime).equals("30:00")) {
                        if (ringTimes == 0) {
                            ++ringTimes;
                            startAlarm();
                            break;

                        }

                    }
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

    private void startAlarm() {
//        Uri uri = Uri.parse(ringuri);
        mMediaPlayer = MediaPlayer.create(this, getSystemDefultRingtoneUri());
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    /**
     * 获取系统当前铃声
     */
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }
}
