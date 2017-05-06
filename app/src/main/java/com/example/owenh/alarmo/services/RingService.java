package com.example.owenh.alarmo.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.util.VibrateUtil;

//TODO 防止后台被清理
//TODO 字体选择
public class RingService extends Service {
    private static final int TYPE_RUN = 1;
    private static final int TYPE_STOP = 0;
    private static final int MSG_KEY_1 = 1;
    private MediaPlayer mMediaPlayer;
    SharedPreferences preferences;
    Context mContext;
    String ringUri = "";
    private int mRun = 1;
    private boolean isVibrate;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_KEY_1:
                    long sysTime = System.currentTimeMillis();
                    if (DateFormat.format("ss", sysTime).equals("00") || DateFormat.format("mm:ss", sysTime).equals("30:00")) {
                        //TODO 增加时间选择
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
        if (ringUri.equals("")) {
            SharedPreferences.Editor editor = preferences.edit();
            ringUri = getSystemDefultRingtoneUri().toString();
            editor.putString("ringUri", ringUri);
            editor.commit();
            mContext = getApplicationContext();
        }
//        preferences = getApplicationContext().getSharedPreferences("Alarmo", MODE_PRIVATE);
        ringUri = preferences.getString("ringUri", "");
        Log.d("huangshaohuaRingService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        String ringStr = preferences2.getString("pref_notification", ringUri);
        isVibrate = preferences2.getBoolean("pref_vibrate", true);
        Log.d("isVibrate",""+isVibrate);
        if (!ringStr.equals(""))
            ringUri = ringStr;
        new TimeThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //java强制结束线程的三种方法http://blog.csdn.net/anhuidelinger/article/details/11746365
//        timeThread.interrupt(); 没有用这句
        mRun = TYPE_STOP;
        Log.i("huangshaohuaRingService", "onDestroy");
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
            } while (mRun == TYPE_RUN);
        }
    }

    private void startAlarm() {
        Ringtone rt = RingtoneManager.getRingtone(this,Uri.parse(ringUri));
        /*mMediaPlayer = MediaPlayer.create(this, Uri.parse(ringUri));
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();*/
        rt.play();
        if (isVibrate)
            VibrateUtil.vibrate(getApplicationContext(), 300);
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
