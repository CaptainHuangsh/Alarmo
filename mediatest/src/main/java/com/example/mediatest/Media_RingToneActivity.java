package com.example.mediatest;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

class Media_RingTongActivity extends Activity {
    //定义三个按钮
    private Button mRingtongButton;
    private Button mAlarmButton;
    private Button mNotificationButton;

    //定义类型
    private static final int RingtongButton=0;
    private static final int AlarmButton=1;
    private static final int NotificationButton=2;

    //铃声文件夹
    private String strRingtongFolder="/sdcard/media/ringtones";
    private String strAlarmFolder="/sdcard/media/alarms";
    private String strNotificationFolder="/sdcard/media/notifications";
    private Parcelable pickedUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media__ring_tone);
        mRingtongButton=(Button)findViewById(R.id.myRingtongButton);
        mRingtongButton.setOnClickListener(new myRingtongButtonListener());
        mAlarmButton=(Button)findViewById(R.id.myAlarmButton);
        mAlarmButton.setOnClickListener((View.OnClickListener) new myAlarmButtonListener());
        mNotificationButton=(Button)findViewById(R.id.myNotificationButton);
        mNotificationButton.setOnClickListener((View.OnClickListener) new myNotificationButtonListener());
    }

    //设置来电铃声监听器
    private class myRingtongButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isFolder(strRingtongFolder)){
                //打开系统铃声设置
                Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI, true);
                //类型为来电ringtong
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                //设置显示的题目
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电的铃声");
                //当设置完成之后返回到当前的activity
                startActivityForResult(intent, RingtongButton);
            }
        }
    }

    //设置闹钟铃声监听器
    private class myAlarmButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isFolder(strAlarmFolder)){
                Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹铃铃声");
                startActivityForResult(intent, AlarmButton);
            }
        }
    }

    //设置通知铃声监听器
    private class myNotificationButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isFolder(strNotificationFolder)){
                Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置通知铃声");
                startActivityForResult(intent, NotificationButton);
            }
        }
    }
    //检查是否存在指定的文件夹，如果不存在就创建
    private boolean isFolder(String strFolder){
        boolean tmp = false;
        File f1 = new File(strFolder);
        if (!f1.exists())
        {
            if (f1.mkdirs())
            {
                tmp = true;
            }
            else
            {
                tmp = false;
            }
        }
        else
        {
            tmp = true;
        }
        return tmp;
    }
    //设置铃声之后的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK){
            return;
        }
        switch(requestCode){
            case RingtongButton:
                try {
                    //得到我们选择的铃声
                    Uri pickedUri=data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    //将我们选择的铃声选择成默认
                    if(pickedUri!=null){
                        RingtoneManager.setActualDefaultRingtoneUri(Media_RingTongActivity.this, RingtoneManager.TYPE_RINGTONE, pickedUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case AlarmButton:
                try {
                    //得到我们选择的铃声
                    Uri pickedUri=data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    //将我们选择的铃声选择成默认
                    if(pickedUri!=null){
                        RingtoneManager.setActualDefaultRingtoneUri(Media_RingTongActivity.this, RingtoneManager.TYPE_ALARM, pickedUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case NotificationButton:
                try {
                    //得到我们选择的铃声
                    pickedUri=data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    //将我们选择的铃声选择成默认
                    if(pickedUri!=null){
                        RingtoneManager.setActualDefaultRingtoneUri(Media_RingTongActivity.this, RingtoneManager.TYPE_NOTIFICATION, (Uri) pickedUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}