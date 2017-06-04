package com.example.owenh.alarmo.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.activity.WatchActivity;
import com.example.owenh.alarmo.util.AlarmoUtil;
import com.example.owenh.alarmo.util.DBManager;

//TODO 防止后台被清理
//TODO 字体选择
public class RingService extends Service {
    public static boolean isRingServiceSurvive = false;

    private static boolean isOneMinAdvance = false;
    private static final int TYPE_RUN = 1;
    private static final int TYPE_STOP = 0;
    private static final int MSG_KEY_1 = 1;
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
                    if (isOneMinAdvance) {
                        sysTime += 1000 * 60;//提前一分
                    }
                    if (WatchActivity.WATCH_STATUS == 1) {
                        //是否是在Watch界面打开的此服务
                        if (DateFormat.format("mm:ss", sysTime).equals("00:00") || DateFormat.format("mm:ss", sysTime).equals("30:00")) {
                            startAlarm();
                            break;
                        }
                    } else {
                        if (DateFormat.format("mm:ss", sysTime).equals("00:00") || DateFormat.format("mm:ss", sysTime).equals("30:00")) {
                            if (AlarmoUtil.getInstance().isRing(DateFormat.format("HH:mm", sysTime).toString())) {
                                //大写HH 24小时制
                                startAlarm();
                            }
                            DBManager.getInstance().closeDatabase();
                            break;
                        }
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
        isRingServiceSurvive = true;//用于判断此服务是否存活
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getApplicationContext().getSharedPreferences("Alarmo", MODE_PRIVATE);
        isRingServiceSurvive = true;//用于判断此服务是否存活
        ringUri = preferences.getString("ringUri", "");
        if (ringUri.equals("")) {
            SharedPreferences.Editor editor = preferences.edit();
            ringUri = getSystemDefaultRingtoneUri().toString();
            editor.putString("ringUri", ringUri);
            editor.apply();
            mContext = getApplicationContext();
        }
        ringUri = preferences.getString("ringUri", "");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mRun = TYPE_RUN;//重启服务时为打开状态
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        String ringStr = preferences2.getString("pref_notification", ringUri);
        isVibrate = preferences2.getBoolean("pref_vibrate", true);
        isOneMinAdvance = preferences2.getBoolean("pref_advance_one", false);//提前一分钟响铃是否打开
        if (!ringStr.equals(""))
            ringUri = ringStr;
        new TimeThread().start();
        isRingServiceSurvive = true;//用于判断此服务是否存活
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRingServiceSurvive = false;//用于判断此服务是否存活
        //java强制结束线程的三种方法http://blog.csdn.net/anhuidelinger/article/details/11746365
//        timeThread.interrupt(); 没有用这句
        mRun = TYPE_STOP;
    }

    /**
     * 建立一个线程，每秒钟刷新一次
     */
    private class TimeThread extends Thread {
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
        Ringtone rt = RingtoneManager.getRingtone(this, Uri.parse(ringUri));
        rt.play();
        if (isVibrate)
            AlarmoUtil.getInstance().vibrate(getApplicationContext(), 300);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取系统当前铃声
     */
    private Uri getSystemDefaultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }
}
