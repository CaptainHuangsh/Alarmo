package com.example.owenh.alarmo;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class Alarm extends Activity {

    private Button mToWatch;
    private Button mToOther;
    private Button mGoRing;
    private MediaPlayer mMediaPlayer = new MediaPlayer();


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
        init();
    }

    public void init() {
        mToWatch.setOnClickListener(new View.OnClickListener() {
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
        /*try{
            File file = new File(Environment.getExternalStorageDirectory(),
                    "ring0.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件
            Log.v(file.getPath()+"","huangshaohua");
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        }catch (Exception e){
            e.printStackTrace();
        }*/
        mGoRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                else {
                    mediaPlayer.reset();
                }*/
                startAlarm();

            }
        });
    }

    public void findview() {
        mToWatch = (Button) findViewById(R.id.to_watch);
        mToOther = (Button) findViewById(R.id.to_other);
        mGoRing = (Button) findViewById(R.id.ring);
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
    }

    /**
     * 获取系统铃声
     */
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }


}
