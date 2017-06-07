package com.example.owenh.alarmo.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.fragment.AlarmoFragment;
import com.example.owenh.alarmo.util.DBManager;
import com.example.owenh.alarmo.util.SPUtils;

public class AlarmMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.app_name));
        init();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new AlarmoFragment())
                    .commit();
        }
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

    public void init() {
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        //强制竖屏
        DBManager.getInstance().openDatabase();
        DBManager.getInstance().closeDatabase();//如果不存在数据库则新建数据库
        String ringUri = (String) SPUtils.getInstance().get(AlarmMain.this, "ringUri", "");
        if ("".equals(ringUri)) {
            //首次打开应用，将铃声设置未系统默认铃声
            ringUri = getSystemDefaultRingtoneUri().toString();
            SPUtils.getInstance().put(this, "ringUri", ringUri);
        }

    }


    /**
     * 获取系统当前铃声，用于初始化Alarmo铃声
     */
    private Uri getSystemDefaultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
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
}
