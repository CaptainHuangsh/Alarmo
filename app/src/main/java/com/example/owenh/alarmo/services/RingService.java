package com.example.owenh.alarmo.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateFormat;

import java.io.IOException;

public class RingService extends Service {
    private static final int MSG_KEY_1 = 1;
    private MediaPlayer mMediaPlayer;
    SharedPreferences preferences;
    String ringUri = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_KEY_1:
                    long sysTime = System.currentTimeMillis();
                    if (DateFormat.format("mm:ss", sysTime).equals("00:00") || DateFormat.format("mm:ss", sysTime).equals("30:00")) {
                            startAlarm();
                            break;

                    }
                    break;

                default:
                    break;
            }
        }
    };


    public RingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getApplicationContext().getSharedPreferences("Alarmo", MODE_PRIVATE);
        ringUri = preferences.getString("ringUri", "");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new TimeThread().start();
        return super.onStartCommand(intent, flags, startId);
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取系统当前铃声
     */
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }
}
