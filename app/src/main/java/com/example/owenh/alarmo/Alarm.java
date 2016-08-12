package com.example.owenh.alarmo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import static android.R.attr.data;

public class Alarm extends Activity implements
        Button.OnClickListener {

    private Button mToWatch;
    private Button mToOther;
    private Button mGoRing;
    private Button mSelectRing;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private Uri pickedUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = Alarm.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.alram_main0);
        findview();
        setListener();
        init();
    }

    public void init() {
        /*mToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Alarm.this, Watch.class);
                startActivity(intent);
            }
        });
        mToOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Alarm.this, Other.class);
                startActivity(intent1);
            }
        });
        *//*try{
            File file = new File(Environment.getExternalStorageDirectory(),
                    "ring0.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件
            Log.v(file.getPath()+"","huangshaohua");
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        }catch (Exception e){
            e.printStackTrace();
        }*//*
        mGoRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               *//* if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                else {
                    mediaPlayer.reset();
                }*//*
                startAlarm();

            }
        });*/
    }

    public void findview() {
        mToWatch = (Button) findViewById(R.id.to_watch);
        mToOther = (Button) findViewById(R.id.to_other);
        mGoRing = (Button) findViewById(R.id.ring);
        mSelectRing = (Button) findViewById(R.id.select_ring);
    }

    public void setListener() {
        mSelectRing.setOnClickListener(this);
        mGoRing.setOnClickListener(this);
        mToOther.setOnClickListener(this);
        mToWatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_watch:
                Intent intent = new Intent(Alarm.this, Watch.class);
                startActivity(intent);
                break;
            case R.id.to_other:
                Intent intent1 = new Intent(Alarm.this, Other.class);
                startActivity(intent1);
                break;
            case R.id.ring:
                startAlarm();
                break;
            case R.id.select_ring:
                showSelectRingDialog();
                break;
        }
    }

    //触发响铃
    private void startAlarm() {
        Log.v("ring1",""+pickedUri);
        mMediaPlayer = MediaPlayer.create(this, pickedUri);
//        mMediaPlayer = MediaPlayer.create(this, getSystemDefultRingtoneUri());
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
     */
    private Uri getSystemDefultRingtoneUri() {

        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }

    //弹出选择铃声对话框
    public void showSelectRingDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select Ringtong Please");
        dialog.setMessage("sss");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //弹出铃声选择框，内容为手机所有铃声
                Intent intentSystemRingtone = new Intent(
                        RingtoneManager.ACTION_RINGTONE_PICKER);
                intentSystemRingtone
                        .putExtra(
                                RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT,
                                false);
                intentSystemRingtone
                        .putExtra(
                                RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT,
                                false);
                intentSystemRingtone
                        .putExtra(
                                RingtoneManager.EXTRA_RINGTONE_TYPE,
                                RingtoneManager.TYPE_NOTIFICATION);
                //TYPE_ALL、TYPE_NOTIFICATION、TYPE_ALARM三个参数可选

                Log.v("intent0",""+intentSystemRingtone);
                pickedUri = intentSystemRingtone.getData();
                Log.v("ring0",""+pickedUri);
                startActivityForResult(
                        intentSystemRingtone, 0);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }


}
