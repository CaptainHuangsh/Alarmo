package com.example.owenh.alarmo.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.owenh.alarmo.R;
import com.example.owenh.alarmo.provider.AlarmoDatabaseHelper;

import java.io.IOException;

public class Alarm extends AppCompatActivity implements
        Button.OnClickListener {

    private ImageButton mToWatch;
//    private ImageButton mToOther;
//    private ImageButton mGoRing;
//    private ImageButton mSelectRing;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private Uri pickedUri = null;
    private AlarmoDatabaseHelper dpHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alram_main01);
        /*//隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = Alarm.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);*/
        setTitle("Alarmo");
        android.app.ActionBar actionBar = getActionBar();
        findview();
        setListener();
        init();
    }

    //重写Activity的onCreateOptionsMenu()方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //响应Action按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_p:
                return true;
            /*case R.id.edit_p:
//                Toast.makeText(this, "edit clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.star:
//                Toast.makeText(this, "star clicked", Toast.LENGTH_SHORT).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void init() {
        dpHelper = new AlarmoDatabaseHelper(this,"Alarmoyri.db",null,1);
        dpHelper.getWritableDatabase();
    }

    public void findview() {
        mToWatch = (ImageButton) findViewById(R.id.to_watch);
//        mToOther = (ImageButton) findViewById(R.id.to_other);
//        mGoRing = (ImageButton) findViewById(R.id.ring);
//        mSelectRing = (ImageButton) findViewById(R.id.select_ring);
    }

    public void setListener() {
//        mSelectRing.setOnClickListener(this);
//        mGoRing.setOnClickListener(this);
//        mToOther.setOnClickListener(this);
        mToWatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_watch:
                Intent intent = new Intent(Alarm.this, Watch.class);
                startActivity(intent);
                break;
         /*   case R.id.to_other:
                Intent intent1 = new Intent(Alarm.this, Other.class);
                startActivity(intent1);
                break;
            case R.id.ring:
                startAlarm();
                break;
            case R.id.select_ring:
                showSelectRingDialog();
                break;*/
        }
    }

    //触发响铃
    private void startAlarm() {
        String ringuri = "";
       /* SQLiteDatabase db = dpHelper.getWritableDatabase();
        Cursor cursor = db.query("Alarmoyri",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                ringuri = cursor.getString(cursor.getColumnIndex("ringuri"));
                Log.d("Watch",ringuri);
            }while (cursor.moveToNext());
        }
        cursor.close();*/
//        Log.v("ring1",""+pickedUri);
        if(pickedUri != null) {
            mMediaPlayer = MediaPlayer.create(this, pickedUri);
//        mMediaPlayer = MediaPlayer.create(this, Uri.parse(ringuri));
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
        dialog.setMessage("选择铃声");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //弹出铃声选择框，内容为手机所有铃声
                //打开系统铃声设置
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
                //设置显示的题目
                intentSystemRingtone.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置铃声");
                //TYPE_ALL、TYPE_NOTIFICATION、TYPE_ALARM三个参数可选

                Log.v("intent0", "" + intentSystemRingtone);
                Log.v("ring0", "" + pickedUri);
                startActivityForResult(
                        intentSystemRingtone, 0);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
    //设置铃声之后的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK){
            return;
        }

                try {
                    //得到我们选择的铃声
                    pickedUri=data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    Log.v("ring0", "" + pickedUri);
                    //将我们选择的铃声选择成默认
                    if(pickedUri!=null){
                        RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION, (Uri) pickedUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


        super.onActivityResult(requestCode, resultCode, data);
        SQLiteDatabase db = dpHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ringuri",pickedUri.toString());
        db.insert("Alarmoyri",null,values);
        values.clear();
    }

}
