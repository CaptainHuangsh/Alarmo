package com.example.owenh.alarmo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.services.RingService;
import com.example.owenh.alarmo.util.VibrateUtil;

//TODO 铃声响铃
//TODO 时间自选
//TODO 颜色自选

public class AlarmMain extends AppCompatActivity implements
        Button.OnClickListener {

    private ImageButton mToWatch;
    private Switch mSwitch;
    private int isChecked = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram_main);
        setTitle("Alarmo");
//        VibrateUtil.vibrate(AlarmMain.this,100);
        init();
        findView();
        setListener();
    }

    //重写Activity的onCreateOptionsMenu()方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //响应Action按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(SettingActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 跳转到另一个Activity
     *
     * @param cls
     */
    private void startActivity(Class<?> cls) {

        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    public void init() {
    }

    public void findView() {
        mToWatch = (ImageButton) findViewById(R.id.to_watch);
        mSwitch = (Switch) findViewById(R.id.on_off_service2);
    }

    public void setListener() {
        mSwitch.setOnClickListener(this);
        mToWatch.setOnClickListener(this);
    }

    //stopService 不能停止服务原因 Service里面新建了线程，而没有在Service的onDestroy()里面结束这个线程你只需要在onDestroy里面加上结束这个线程的语句就行了
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_watch:
                Intent intent = new Intent(AlarmMain.this, WatchActivity.class);
                startActivity(intent);
                break;
            case R.id.on_off_service2:
                Intent serviceIntent = new Intent(AlarmMain.this, RingService.class);
                if (isChecked == 0) {
                    Log.i("huangshaohuaSwitch", "on");
                    startService(serviceIntent);
                    isChecked++;
                } else {
                    Log.i("huangshaohuaSwitch", "off");
                    stopService(serviceIntent);
                    isChecked = 0;
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取系统铃声
     */
    private Uri getSystemDefultRingtoneUri() {

        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
    }


}
