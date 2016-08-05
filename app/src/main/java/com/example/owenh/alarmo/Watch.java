package com.example.owenh.alarmo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

import static com.example.owenh.alarmo.R.id.ring;

/**
 * Created by owenh on 2016/8/5.
 */

public class Watch extends Activity {

    private TextView mVTime;
    private TextView mSec;
    private MediaPlayer mMediaPlayer;
    private static final int msgKey1 = 1;
    private int ringTimes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=Watch.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.watch);
        findview();
        init();
        new Watch.TimeThread().start();
    }
    public void init(){


    }
    public void findview(){
        mVTime = (TextView)findViewById(R.id.alarm_vtime);
        mSec = (TextView)findViewById(R.id.alarm_vsec);
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
                    ringTimes = 0;
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm", sysTime);
                    CharSequence sysTimeStrsec = DateFormat.format("ss",sysTime);
                    mVTime.setText(sysTimeStr+"");
                    mSec.setText(" "+sysTimeStrsec);
                    if (DateFormat.format("mm:ss", sysTime).equals("03:30")||DateFormat.format("mm:ss", sysTime).equals("30:00")){
                        if(ringTimes == 0){
                            ++ringTimes;
                            startAlarm();break;

                        }

                    }
                    break;

                default:
                    break;
            }
        }
    };
    private void startAlarm() {
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
     * 获取系统铃声
     * */
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }
}
